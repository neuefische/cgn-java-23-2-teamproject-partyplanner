import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import Button from "@mui/material/Button";


type Props = {
    onAddParty: (data: { date: string; location: string; theme: string }) => void;
}

export default function AddForm(props: Props) {

    const [theme, setTheme] = useState("");
    const [date, setDate] = useState("");
    const [location, setLocation] = useState("");

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
                    <legend>Add new Party</legend>
                    <label htmlFor="theme">Theme: </label>
                    <input
                        onChange={event => setTheme(event.target.value)}
                        value={theme}
                        name="theme"
                        id="theme"
                        type="text"
                    required/>
                <label htmlFor="date">Date: </label>
                <input
                    onChange={event => setDate(event.target.value)}
                    value={date}
                    name="date"
                    id="date"
                    type="date"
                    required/>
                <label htmlFor="location">Location: </label>
                <input
                    onChange={event => setLocation(event.target.value)}
                    value={location}
                    name="location"
                    id="location"
                    type="text"
                    required/>
                </fieldset>
                <button>Submit</button>


            </form>

            <Button variant="contained" disableElevation onClick={() => navigate("/")}>Back to List</Button>
        </>
    )
}

