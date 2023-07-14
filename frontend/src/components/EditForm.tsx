import Button from "@mui/material/Button";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {DTOParty} from "../models.ts";
import {TextField} from "@mui/material";

type Props = {
    onEditParty: (id: string, data: DTOParty) => void;
}

export default function EditForm(props: Props) {

    const [id, setId] = useState<string>("")
    const [theme, setTheme] = useState<string>("")
    const [date, setDate] = useState<string>("")
    const [location, setLocation] = useState<string>("")
    const params = useParams();
    const navigate = useNavigate()
    const [errorTheme, setErrorTheme] = useState<string>("")
    const [errorDate, setErrorDate] = useState<string>("")
    const [errorLocation, setErrorLocation] = useState<string>("")

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
        const data: DTOParty = {
            location: location,
            theme: theme,
            date: date
        }
        props.onEditParty(id, data);
        navigate(`/${id}`);
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
        const currentDate = new Date() // "2023-07-13T13:43:54.124+02:00"
        currentDate.setHours(0, 0, 0) // "2023-07-13"
        const givenDate = new Date(event.target.value) // "2023-07-13"
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
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Edit Party</legend>
                    <TextField error={errorTheme.length > 0}
                        label="Theme"
                        type="text"
                        value={theme}
                        id="theme"
                        onChange={changeTheme}
                        helperText={errorTheme}
                    />
                    <TextField error={errorDate.length > 0}
                        label="Date"
                        type="date"
                        value={date}
                        id="date"
                        onChange={changeDate}
                        helperText={errorDate}

                    />
                    <TextField error={errorLocation.length > 0}
                        label="Location"
                        type="text"
                        value={location}
                        id="location"
                        onChange={changeLocation}
                        helperText={errorLocation}

                    />
                </fieldset>
                <Button sx={{mt: 1, mr: 1, color: "rgb(44, 161, 173)", borderColor: "rgb(44, 161, 173)"}}
                        variant="outlined" disableElevation
                        onClick={() => navigate(`/${id}`)}>Cancel</Button>

                <Button sx={{mt: 1, mr: 1, bgColor: "rgb(44, 161, 173)"}} type="submit" variant="contained"
                        className="button-right">
                    Submit</Button>
            </form>
        </>
    )
}
