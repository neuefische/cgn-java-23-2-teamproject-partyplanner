import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import Button from "@mui/material/Button";


type Props = {
    onAddParty: (data: { date: string; location: string; theme: string }) => void;
}

export default function AddForm(props: Props) {

    const [theme, setTheme] = useState<string>("");
    const [date, setDate] = useState<string>("");
    const [location, setLocation] = useState<string>("");

    const navigate = useNavigate()
    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const data = {
            location: location,
            theme: theme,
            date: date
        }
        props.onAddParty(data)
        setDate("")
        setTheme("")
        setLocation("")
        navigate("/")
    }

    return (<>
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Add new Party</legend>
                    <label htmlFor="theme">Theme: </label>
                    <input
                        onChange={event => setTheme(event.target.value)}
                        value={theme}
                        name="theme"
                        id="theme"
                        type="text"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                    <label htmlFor="date">Date: </label>
                    <input
                        onChange={event => setDate(event.target.value)}
                        value={date}
                        name="date"
                        id="date"
                        type="date"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                    <label htmlFor="location">Location: </label>
                    <input
                        onChange={event => setLocation(event.target.value)}
                        value={location}
                        name="location"
                        id="location"
                        type="text"
                        required
                        style={{marginLeft: '20px', marginRight: '20px'}}/>
                </fieldset>
                <Button sx={{mt: 1, mr: 1}} type="submit" variant="outlined" className="button-right">
                    Submit
                </Button>


            </form>

            <Button variant="contained" disableElevation onClick={() => navigate("/")}>Back to List</Button>
        </>
    )
}

