import './App.css'
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import {Party, PartyWithoutId, Quiz} from "./models.ts";
import axios from "axios";
import {Alert, Container, Snackbar, Stack} from "@mui/material";
import {Route, Routes, useNavigate} from "react-router-dom";
import Button from '@mui/material/Button';
import PartyDetail from "./components/PartyDetail.tsx";
import LoginForm from "./components/LoginForm.tsx";
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";
import QuizCard from "./components/QuizCard.tsx";
import RegisterForm from "./components/RegisterForm.tsx";
import EditPage from "./components/EditPage.tsx";
import AddPage from "./components/AddPage.tsx";
import PartyCard from "./components/PartyCard.tsx";


export default function App() {
    const [parties, setParties] = useState<Party[]>([]);
    const [quiz, setQuiz] = useState<Quiz>();
    const [isDeleteSuccess, setIsDeleteSuccess] = useState<boolean>(false);
    const [isEditSuccess, setIsEditSuccess] = useState<boolean>(false);
    const [isAddSuccess, setIsAddSuccess] = useState<boolean>(false);
    const [user, setUser] = useState<string>();
    const [userId, setUserId] = useState<string>();
    const [snackbarStatus, setSnackbarStatus] = useState<boolean>(false);
    const [openWarningToast, setOpenWarningToast] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        fetchParties();
        me();
    }, [user])

    useEffect(() => {
        axios.get("/api/quiz")
            .then(response => response.data)
            .catch(console.error)
            .then(data => {
                console.log(data)
                setQuiz(data)
            });
    }, []);

    function me() {
        axios.get('api/user/me')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setUser(data))

        axios.get('api/user')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setUserId(data))
    }

    function fetchParties() {
        axios.get('api/parties')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }

    function handleAddParty(data: PartyWithoutId) {
        axios.post('api/parties', data)
            .then(response => response.data)
            .catch(error => {
                console.error(error);
                setOpenWarningToast(true);
            })
            .then(data => {
                setParties(data)
            });
        navigate("/")
        setIsAddSuccess(true)
        const timeoutId = setTimeout(() => {
            setIsAddSuccess(false)
        }, 4000)
        return () => {
            clearTimeout(timeoutId)
        }
    }

    function handleEditParty(id: string, data: PartyWithoutId) {
        axios.put(`/api/parties/${id}`, data)
            .then(response => response.data)
            .catch(error => {
                console.error(error);
                setOpenWarningToast(true);
            })
            .then(data => {
                setParties(
                    parties.map(party => {
                        if (party.id === id) {
                            return data
                        }
                        return party
                    })
                );
            })
        navigate(`/${id}`)
        setIsEditSuccess(true)
        const timeoutId = setTimeout(() => {
            setIsEditSuccess(false)
        }, 8000)
        return () => {
            clearTimeout(timeoutId)
        }
    }

    function handleDeleteParty(id: string) {
        axios.delete(`/api/parties/${id}`)
            .catch(error => {
                console.error(error);
                setOpenWarningToast(true);
            })
        setParties(parties.filter(party => party.id !== id))
        setIsDeleteSuccess(true)
        const timeoutId = setTimeout(() => {
            setIsDeleteSuccess(false)
        }, 4000)
        navigate("/")
        return () => {
            clearTimeout(timeoutId)
        }
    }

    function handleSolveQuiz(id: string) {
        axios.get(`/api/quiz/${id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => setQuiz(data))
    }

    function handleLogin(username: string, password: string) {
        axios.post("/api/user/login", null, {auth: {username, password}})
            .then(response => response.data)
            .catch(console.error)
            .then(data => setUser(data))
    }

    function handleLogout() {
        axios.post("/api/user/logout")
            .catch(console.error)
        setUser(undefined)
    }

    function handleRegister(username: string, password: string) {
        axios.post("/api/user/register", {username: username, password: password})
            .catch(() => setSnackbarStatus(true))
    }

    return <main>
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
            {openWarningToast && (
                <Alert severity="warning">Are you logged in?</Alert>
                )}
        </Stack>
        <Routes>
            <Route path={"/login"} element={<LoginForm onLogin={handleLogin}/>}/>
            <Route path={"/register"} element={<RegisterForm onRegister={handleRegister} onLogin={handleLogin}/>}/>
            <Route path={"/add"} element={<ProtectedRoutes user={user}/>}>
                <Route path={""} element={<AddPage onAddParty={handleAddParty}/>}/>
            </Route>
            <Route path={"/:id/edit"} element={<ProtectedRoutes user={user}/>}>
                <Route path={""} element={<EditPage onEditParty={handleEditParty}/>}/>
            </Route>
            <Route path={"/:id"}>
                <Route index element={<PartyDetail userId={userId} user={user} onDeleteParty={handleDeleteParty}/>}/>
            </Route>

            <Route path={"/"} element={
                (<Container sx={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                    <Button sx={{bgcolor: "rgb(44, 161, 173)"}} variant="contained"
                            disableElevation
                            onClick={() => navigate("/add")}>
                        + Add Party
                    </Button>
                    <PartyCard parties ={parties} user={user} userId={userId}/>
                    {quiz ? <QuizCard quiz={quiz} onSolveQuiz={handleSolveQuiz}/> : <>Loading quiz...</>}
                </Container>)
            }/>
        </Routes>
        <Snackbar open={snackbarStatus} autoHideDuration={6000} onClose={() => setSnackbarStatus(false)}>
            <Alert onClose={() => setSnackbarStatus(false)} severity="error" sx={{width: '100%'}}>
                Username already in use!
            </Alert>
        </Snackbar>
    </main>
}
