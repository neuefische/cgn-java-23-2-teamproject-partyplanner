import {FormEvent, useEffect, useState} from "react";
import Button from "@mui/material/Button";
import {PartyWithoutId} from "../models.ts";
import {Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";

type Props = {
    onSubmitParty: (data: PartyWithoutId) => void
    party: PartyWithoutId | undefined
    legend: string
    backUrl: string
    placeholder: string
}

export default function InputForm(props: Props) {

    const [theme, setTheme] = useState<string>("");
    const [date, setDate] = useState<string>("");
    const [location, setLocation] = useState<string>("");
    const [errorTheme, setErrorTheme] = useState<string>("")
    const [errorDate, setErrorDate] = useState<string>("")
    const [errorLocation, setErrorLocation] = useState<string>("")

    const navigate = useNavigate()

    useEffect(() => {
        if (typeof props.party !== "undefined") {
            setTheme(props.party.theme)
            setDate(props.party.date)
            setLocation(props.party.location)
        }
    }, [props.party])


    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const data: PartyWithoutId = {
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




    const[open, setOpen] = useState(false);
    const handleClickOpen = () => {
            setOpen(true);
        }

        const handleClose = () => {
            setOpen(false);
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

    return <form onSubmit={handleSubmit}>
        <fieldset>
            <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>{props.legend}</legend>
            <TextField sx={{ml: 2, width: '85%'}}
                       error={errorTheme.length > 0}
                       label="Theme"
                       type="text"
                       value={theme}
                       id="theme"
                       placeholder="choose Theme"
                       required
                       onChange={handleChangeTheme}
                       helperText={errorTheme}
            />
            <TextField sx={{ml: 2, width: '85%'}}
                       error={errorDate.length > 0}
                       label="Date"
                       type="date"
                       value={date}
                       id="date"
                       required
                       onChange={handleChangeDate}
                       helperText={errorDate}
            />
            <TextField sx={{ml: 2, width: '85%'}}
                       error={errorLocation.length > 0}
                       label="Location"
                       type="text"
                       value={location}
                       id="location"
                       placeholder="choose Location"
                       required
                       onChange={handleChangeLocation}
                       helperText={errorLocation}
            />
            <div>
                <Button sx={{mt: 1, mr: 1, color: "rgb(44, 161, 173)", borderColor: "rgb(44, 161, 173)"}}
                        variant="outlined" disableElevation
                        onClick={handleClickOpen}> Cancel</Button>
                <Dialog
                    open={open}
                    keepMounted
                    onClose={handleClose}
                    aria-describedby="alert-dialog-description"
                >
                    <DialogTitle>{"Go back without saving?"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-description">
                            Are you sure you want to go back to the party, without saving?
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleClose}>Disagree</Button>
                        <Button onClick={() => navigate(props.backUrl)} color="error" variant="outlined">Go back</Button>
                    </DialogActions>
                </Dialog>

                <Button sx={{mt: 1, mr: 1, bgcolor: "rgb(44, 161, 173)"}} type="submit" variant="contained"
                        className="button-right"
                        disabled={errorTheme.length > 0 || errorDate.length > 0 || errorLocation.length > 0}>
                    Submit
                </Button>
            </div>
        </fieldset>
    </form>
}
