import './App.css'
import Partylist from "./components/Partylist.tsx";
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import {Party} from "./models.ts";
import axios from "axios";

export default function App() {
    const [parties, setParties] = useState<Party[]>([]);

    useEffect(() => {
        axios.get('api/parties')
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParties(data))
    }, [])

  return (
    <main>
      <Header/>
      <Partylist parties={parties}/>
    </main>
  )
}


