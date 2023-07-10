import './App.css'
import Partylist from "./components/Partylist.tsx";
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import {DTOParty, Party, Quiz} from "./models.ts";
import axios from "axios";
import {Alert, Container, Stack} from "@mui/material";
import AddForm from "./components/AddForm.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import Button from '@mui/material/Button';
import PartyDetail from "./components/PartyDetail.tsx";
import EditForm from "./components/EditForm.tsx";
import QuizCard from "./components/QuizCard.tsx";


export default function App() {
    const navigate = useNavigate();
    const [parties, setParties] = useState<Party[]>([]);
    const [quiz, setQuiz] = useState<Quiz>();


    const [isDeleteSuccess, setIsDeleteSuccess] = useState(false);
    const [isEditSuccess, setIsEditSuccess] = useState(false);
    const [isAddSuccess, setIsAddSuccess] = useState(false);

    useEffect(() => {
        axios.get('api/parties')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }, [])

    useEffect(() => {
        axios.get("/api/quiz")
            .then(response => response.data)
            .catch(console.error)
            .then(data => {
                console.log(data)
                setQuiz(data)
            });
    }, []);

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

    return (
        <main>
            <Routes>
                <Route path={"/add"} element={<AddForm onAddParty={handleAddParty}/>}/>
                <Route path={"/:id"}>
                    <Route index element={<PartyDetail onDeleteParty={handleDeleteParty}/>}/>
                    <Route path={"edit"} element={<EditForm onEditParty={handleEditParty}/>}/>
                </Route>

                <Route path={"/"} element={
                    (<Container sx={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                        <Header/>
                        <Stack sx={{ width: '100%' }}>
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
                        <Partylist parties={parties}/>
                        <Button className="button-right" variant="contained" disableElevation
                                onClick={() => navigate("/add")}>
                            + Add Party
                        </Button>
                        {quiz ? <QuizCard quiz={quiz}/> : <>Loading quiz...</>}
                    </Container>)
                }/>
            </Routes>
        </main>
    )
}
