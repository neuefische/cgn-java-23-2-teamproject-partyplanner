import './App.css'
import Partylist from "./components/Partylist.tsx";
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import {DTOParty, Party} from "./models.ts";
import axios from "axios";
import {Alert, Container, Stack} from "@mui/material";
import AddForm from "./components/AddForm.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import Button from '@mui/material/Button';
import PartyDetail from "./components/PartyDetail.tsx";
import EditForm from "./components/EditForm.tsx";
import LoginForm from "./components/LoginForm.tsx";
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";


export default function App() {
    const navigate = useNavigate();
    const [parties, setParties] = useState<Party[]>([]);

    const [isDeleteSuccess, setIsDeleteSuccess] = useState(false);
    const [isEditSuccess, setIsEditSuccess] = useState(false);
    const [isAddSuccess, setIsAddSuccess] = useState(false);
    const [user, setUser] = useState<string>();

    useEffect(() => {
        fetchParties();
        me();
    }, [user])

    function me() {
        axios.get('api/user/me2')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setUser(data))
    }

    function fetchParties() {
        axios.get('api/parties')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }

    function handleAddParty(data: DTOParty) {
        axios.post('api/parties', data)
            .then(response => response.data)
            .catch(console.error)
            .then(data => {
                setParties(data)
                setIsAddSuccess(true)
                setTimeout(() => {
                    setIsAddSuccess(false)
                }, 4000)
            });
    }

    function handleEditParty(id: string, data: DTOParty) {
        axios.put(`/api/parties/${id}`, data)
            .then(response => response.data)
            .catch(console.error)
            .then(data => {
                setParties(
                    parties.map(party => {
                        if (party.id === id) {
                            return data
                        }
                        return party
                    })
                );
                setIsEditSuccess(true)
                setTimeout(() => {
                    setIsEditSuccess(false)
                }, 8000)
            })
    }

    function handleDeleteParty(id: string) {
        axios.delete(`/api/parties/${id}`)
            .catch(console.error)
        setParties(parties.filter(party => party.id !== id))
        setIsDeleteSuccess(true)
        setTimeout(() => {
            setIsDeleteSuccess(false)
        }, 4000)
        navigate("/")
    }

    function handleLogin(username: string, password: string) {
        axios.post("/api/user/login", null, {auth: {username, password}})
            .then(response => response.data)
            .catch(console.error)
            .then(data => setUser(data))
    }

    function handleLogout() {
        axios.post("/api/user/logout")
            .then(() => window.location.reload())
            .catch(console.error)
    }

    return (
        <main>
            <Header user={user} onLogout={handleLogout}/>
            <Stack sx={{width: '100%', m: 0, p: 0,}}>
                {isDeleteSuccess && (
                    <Alert severity="error">You just deleted your Party!</Alert>
                )}
                {isEditSuccess && (
                    <Alert severity="success">You edited your Party successfully!</Alert>
                )}
                {isAddSuccess && (
                    <Alert severity="success">You added your Party successfully!</Alert>
                )}
            </Stack>
            <Routes>
                <Route path={"/login"} element={<LoginForm onLogin={handleLogin}/>}/>
                <Route path={"/add"} element={<ProtectedRoutes user={user}/>}>
                    <Route path={""} element={<AddForm onAddParty={handleAddParty}/>}/>
                </Route>
                <Route path={"/:id/edit"} element={<ProtectedRoutes user={user}/>}>
                    <Route path={""} element={<EditForm onEditParty={handleEditParty}/>}/>
                </Route>
                <Route path={"/:id"}>
                    <Route index element={<PartyDetail user={user} onDeleteParty={handleDeleteParty}/>}/>
                </Route>

                <Route path={"/"} element={
                    (<Container>
                        <Partylist parties={parties}/>
                        <Button sx={{bgcolor: "rgb(44, 161, 173)"}} className="button-right" variant="contained"
                                disableElevation
                                onClick={() => navigate("/add")}>
                            + Add Party
                        </Button>
                    </Container>)
                }/>
            </Routes>
        </main>
    )
}
