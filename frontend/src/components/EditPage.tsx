import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {PartyWithoutId, Party} from "../models.ts";
import InputForm from "./InputForm.tsx";

type Props = {
    onEditParty: (id: string, data: PartyWithoutId) => void;
}

export default function EditPage(props: Props) {
    const [id, setId] = useState<string>("")
    const [party, setParty] = useState<Party>()

    const params = useParams();

    useEffect(() => {
        axios.get(`/api/parties/${params.id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => {
                setId(data.id)
                setParty(data)
            })
    }, [params.id])

    function handleSubmit(editedParty: PartyWithoutId) {
        props.onEditParty(id, editedParty)
    }

    const partyWithoutID: PartyWithoutId = typeof party !== "undefined"
        ? {theme: party.theme, date: party.date, location: party.location}
        : {theme: "", date: "", location: ""}

    return party &&
        <InputForm onSubmitParty={handleSubmit} party={partyWithoutID} legend="Edit Party" backUrl={`/${id}`}/>
}
