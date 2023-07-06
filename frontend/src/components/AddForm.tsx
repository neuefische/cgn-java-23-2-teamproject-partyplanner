import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import Button from "@mui/material/Button";
import ControlledInput from "./ControlledInput.tsx";


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
                        id="locatioin"
                        onChange={setLocation}
                    />
                </fieldset>
                <Button sx={{mt: 1, mr: 1}} variant="outlined" disableElevation onClick={() => navigate("/")}> Back to
                    List</Button>

                <Button sx={{mt: 1, mr: 1}} type="submit" variant="contained" className="button-right">
                    Submit
                </Button>


            </form>
        </>
    )
}
