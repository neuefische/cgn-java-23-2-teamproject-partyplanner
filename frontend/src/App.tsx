
import './App.css'
import Partylist from "./components/Partylist.tsx";
import {parties} from "./parties.ts";
import Header from "./components/Header.tsx";

export default function App() {



  return (
    <>
        <Header/>
      <Partylist parties={parties}/>


    </>
  )
}


