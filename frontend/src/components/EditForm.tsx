import Button from "@mui/material/Button";
import {FormEvent, useEffect, useState} from "react";
import {Party} from "../models.ts";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

type Props = {
    onEditParty: (id: string, data: { [p: string]: File | string }) => void;
}

export default function EditForm(props: Props) {

    const [party, setParty] = useState<Party>()

    const params = useParams();
    const navigate = useNavigate()

    useEffect(() => {
        axios.get(`api/parties/${params.id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParty(data))
    }, [params.id])

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = Object.fromEntries(formData);
        console.log("data: ", data)

        props.onEditParty(party.id, data);
    }


    return (<>

            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Edit Party</legend>
                    <label htmlFor="theme">Theme: </label>
                    <input
                        value={party?.theme}
                        name="theme"
                        id="theme"
                        type="text"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                    <label htmlFor="date">Date: </label>
                    <input
                        value={party?.date}
                        name="date"
                        id="date"
                        type="date"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                    <label htmlFor="location">Location: </label>
                    <input
                        value={party?.location}
                        name="location"
                        id="location"
                        type="text"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                </fieldset>
                <Button sx={{mt: 1, mr: 1}} variant="outlined" disableElevation
                        onClick={() => navigate(`/${party?.id}`)}> Back to
                    List</Button>

                <Button sx={{mt: 1, mr: 1}} type="submit" variant="contained" className="button-right">
                    Submit
                </Button>


            </form>
        </>
    )

}