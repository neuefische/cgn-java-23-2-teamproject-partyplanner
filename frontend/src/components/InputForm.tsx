import {FormEvent, useEffect, useState} from "react";
import Button from "@mui/material/Button";
import {DTOParty} from "../models.ts";
import {TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";


type Props = {
    onSubmitParty: (data: DTOParty) => void;
    party: DTOParty | undefined
    legend: string
    backUrl: string
}

export default function InputForm(props: Props) {

    const [theme, setTheme] = useState<string | undefined>(props.party?.theme);
    const [date, setDate] = useState<string | undefined>(props.party?.date);
    const [location, setLocation] = useState<string | undefined>(props.party?.location);
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
        props.onSubmitParty(data)
    }

    function handleChangeTheme(event: React.ChangeEvent<HTMLInputElement>) {
        setTheme(event.target.value)

        if (event.target.value.length < 3) {
            setErrorTheme("Input is too short!")
        } else if (event.target.value.length > 25) {
            setErrorTheme("Input is too long!")
        } else {
            setErrorTheme("")
        }
    }

    function handleChangeLocation(event: React.ChangeEvent<HTMLInputElement>) {
        setLocation(event.target.value)

        if (event.target.value.length < 3) {
            setErrorLocation("Input is too short!")
        } else if (event.target.value.length > 25) {
            setErrorLocation("Input is too long!")
        } else {
            setErrorLocation("")
        }
    }

    function handleChangeDate(event: React.ChangeEvent<HTMLInputElement>) {
        const currentDate = new Date()
        currentDate.setHours(0, 0, 0)
        const givenDate = new Date(event.target.value)
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
                    <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>{props.legend}</legend>
                    <TextField error={errorTheme.length > 0}
                               label="Theme"
                               type="text"
                               value={theme}
                               id="theme"
                               required
                               onChange={handleChangeTheme}
                               helperText={errorTheme}
                    />
                    <TextField error={errorDate.length > 0}
                               label="Date"
                               type="date"
                               value={date}
                               id="date"
                               required
                               onChange={handleChangeDate}
                               helperText={errorDate}
                    />
                    <TextField error={errorLocation.length > 0}
                               label="Location"
                               type="text"
                               value={location}
                               id="location"
                               required
                               onChange={handleChangeLocation}
                               helperText={errorLocation}
                    />

                    <div>
                        <Button sx={{mt: 1, mr: 1, color: "rgb(44, 161, 173)", borderColor: "rgb(44, 161, 173)"}}
                                variant="outlined" disableElevation
                                onClick={() => navigate(props.backUrl)}> Cancel</Button>

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
