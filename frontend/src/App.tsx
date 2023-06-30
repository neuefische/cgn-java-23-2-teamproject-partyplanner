import './App.css'
import Partylist from "./components/Partylist.tsx";
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import {Party} from "./models.ts";
import axios from "axios";
import {Container} from "@mui/material";
import AddForm from "./components/AddForm.tsx";

export default function App() {
    const [parties, setParties] = useState<Party[]>([]);

    useEffect(() => {
        axios.get('api/parties')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }, [])

  return (
    <Container maxWidth="xl">
      <Header/>
        <Partylist parties={parties}/>
        <AddForm/>
    </Container>
  )
}



