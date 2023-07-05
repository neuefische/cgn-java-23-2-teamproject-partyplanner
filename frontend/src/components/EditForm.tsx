import Button from "@mui/material/Button";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

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
                setDate(data.date)
                setLocation(data.location)
            })
    }, [params.id])

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const data = {
            location: location,
            theme: theme,
            date: date
        }
        props.onEditParty(id, data);
    }


    return (<>

            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Edit Party</legend>
                    <label htmlFor="theme">Theme: </label>
                    <input
                        onChange={event=> setTheme(event.target.value)}
                        value={theme}
                        name="theme"
                        id="theme"
                        type="text"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                    <label htmlFor="date">Date: </label>
                    <input
                        onChange={event=> setDate(event.target.value)}
                        value={date}
                        name="date"
                        id="date"
                        type="date"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                    <label htmlFor="location">Location: </label>
                    <input
                        onChange={event=> setLocation(event.target.value)}
                        value={location}
                        name="location"
                        id="location"
                        type="text"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
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