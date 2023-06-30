import {FormEvent, useState} from "react";


export default function AddForm() {

    const [theme, setTheme] = useState("");
    const [date, setDate] = useState("");
    const [location, setLocation] = useState("");

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        console.log("Theme: ", theme)
        console.log("Date: ", typeof date)
        console.log("Location: ", location)
    }

    return (
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
    )
}

