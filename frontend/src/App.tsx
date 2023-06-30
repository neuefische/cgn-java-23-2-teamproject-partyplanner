import './App.css'
import Partylist from "./components/Partylist.tsx";
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import {Party} from "./models.ts";
import axios from "axios";
import {Container} from "@mui/material";
import AddForm from "./components/AddForm.tsx";
import {Link, Route, Routes} from "react-router-dom";

export default function App() {
    const [parties, setParties] = useState<Party[]>([]);

    useEffect(() => {
        axios.get('api/parties')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }, [])

    function handleAddParty(data: { location: string, theme: string, date: string }) {
        axios.post('api/parties', data)
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }

    return (
        <>
            <button><Link to={"/add"}>Add Party</Link></button>
            <button><Link to={"/"}>Home</Link></button>
            <Routes>
                <Route path={"/add"} element={<AddForm onAddParty={handleAddParty}/>}/>
                <Route path={"/"} element={
                    (<Container>
                        <Header/>
                        <Partylist parties={parties}/>
                    </Container>)
                }/>
            </Routes>
        </>
    )
}



