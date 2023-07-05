import Button from "@mui/material/Button";
import {useEffect, useState} from "react";
import {Party} from "../models.ts";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

export default function EditForm() {

    const [party, setParty] = useState<Party>()

    const params = useParams();
    const navigate = useNavigate()

    useEffect(() => {
        axios.get(`api/parties/${params.id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParty(data))
    }, [])

    function handleSubmit() {

    }


    return (<>

            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Add new Party</legend>
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