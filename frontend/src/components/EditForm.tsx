import Button from "@mui/material/Button";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import ControlledInput from "./ControlledInput.tsx";

type Props = {
    onEditParty: (id: string, data: { date: string; location: string; theme: string }) => void;
}

export default function EditForm(props: Props) {

    const [id, setId] = useState<string>("")
    const [theme, setTheme] = useState<string>("")
    const [date, setDate] = useState<string>("")
    const [location, setLocation] = useState<string>("")

    const params = useParams();
    const navigate = useNavigate()

    useEffect(() => {
        axios.get(`/api/parties/${params.id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => {
                setId(data.id)
                setTheme(data.theme)
                setDate((data.date.split('T')[0]))
                setLocation(data.location)
            })
    }, [params.id])

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const data = {
            id: id,
            location: location,
            theme: theme,
            date: date
        }
        props.onEditParty(id, data);
        navigate(`/${id}`);
    }


    return (<>

            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Edit Party</legend>
                    <ControlledInput
                        label="Theme"
                        type="text"
                        value={theme}
                        id="theme"
                        onChange={setTheme}
                    />
                    <ControlledInput
                        label="Date"
                        type="date"
                        value={date}
                        id="date"
                        onChange={setDate}
                    />
                    <ControlledInput
                        label="Location"
                        type="text"
                        value={location}
                        id="location"
                        onChange={setLocation}
                    />
                </fieldset>
                <Button sx={{mt: 1, mr: 1}} variant="outlined" disableElevation
                        onClick={() => navigate(`/${id}`)}>Cancel </Button>

                <Button sx={{mt: 1, mr: 1}} type="submit" variant="contained" className="button-right">
                    Submit
                </Button>
            </form>
        </>
    )
}
