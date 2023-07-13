import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import Button from "@mui/material/Button";
import {DTOParty} from "../models.ts";
import {TextField} from "@mui/material";


type Props = {
    onAddParty: (data: DTOParty) => void;
}

export default function AddForm(props: Props) {

    const [theme, setTheme] = useState<string>("");
    const [date, setDate] = useState<string>("");
    const [location, setLocation] = useState<string>("");
    const [errorTheme, setErrorTheme] = useState<string>("")
    const [errorDate, setErrorDate] = useState<string>("")
    const [errorLocation, setErrorLocation] = useState<string>("")

    const navigate = useNavigate()

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const data: DTOParty = {
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

    function changeTheme(event: React.ChangeEvent<HTMLInputElement>) {
        setTheme(event.target.value)

        if (event.target.value.length < 3) {
            setErrorTheme("Input is too short!")
        } else if (event.target.value.length > 25) {
            setErrorTheme("Input is too long!")
        } else {
            setErrorTheme("")
        }
    }

    function changeLocation(event: React.ChangeEvent<HTMLInputElement>) {
        setLocation(event.target.value)

        if (event.target.value.length < 3) {
            setErrorLocation("Input is too short!")
        } else if (event.target.value.length > 25) {
            setErrorLocation("Input is too long!")
        } else {
            setErrorLocation("")
        }
    }

    function changeDate(event: React.ChangeEvent<HTMLInputElement>) {
        const currentDate = new Date()
        const currentDay = currentDate.toDateString().split("T")[0]+"T00:00:00.000+00:00"
        const givenDate = new Date(currentDay)
        setDate(event.target.value)

        if (givenDate.getTime() < currentDate.getTime()) {
            setErrorDate("Date must be in the present or future!")
        } else {
            setErrorDate("")
        }
    }

    return (<>

            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Add new Party</legend>
                    <TextField error={errorTheme.length > 0}
                               label="Theme"
                               type="text"
                               value={theme}
                               id="theme"
                               required
                               onChange={changeTheme}
                               helperText={errorTheme}
                    />
                    <TextField error={errorDate.length > 0}
                               label="Date"
                               type="date"
                               value={date}
                               id="date"
                               required
                               onChange={changeDate}
                               helperText={errorDate}
                    />
                    <TextField error={errorLocation.length > 0}
                               label="Location"
                               type="text"
                               value={location}
                               id="location"
                               required
                               onChange={changeLocation}
                               helperText={errorLocation}
                    />

                    <div>
                        <Button sx={{mt: 1, mr: 1, color: "rgb(44, 161, 173)", borderColor: "rgb(44, 161, 173)"}}
                                variant="outlined" disableElevation
                                onClick={() => navigate("/")}> Back to
                            List</Button>

                        <Button sx={{mt: 1, mr: 1, bgcolor: "rgb(44, 161, 173)"}} type="submit" variant="contained"
                                className="button-right"
                                disabled={errorTheme.length > 0 || errorDate.length > 0 || errorLocation.length > 0}>
                            Submit
                        </Button>
                    </div>
                </fieldset>


            </form>
        </>
    )
}
