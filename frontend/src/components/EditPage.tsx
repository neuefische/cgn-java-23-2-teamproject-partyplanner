import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {DTOParty, Party} from "../models.ts";
import InputForm from "./InputForm.tsx";

type Props = {
    onEditParty: (id: string, data: DTOParty) => void;
}

export default function EditPage(props: Props) {

    const [party, setParty] = useState<Party>()

    const params = useParams();

    useEffect(() => {
        axios.get(`/api/parties/${params.id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParty(data))
    }, [params.id])

    const partyWithoutID = {theme: party?.theme, date: party?.date, location: party?.location}



    return (
        <InputForm onSubmitParty={props.onEditParty} party={partyWithoutID} legend="Edit Party" backUrl={`/${params.id}`}/>
    )
}

