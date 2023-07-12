import ControlledInput from "./ControlledInput.tsx";
import Button from "@mui/material/Button";
import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";



type Props = {
    onLogin: (username: string, password: string) => void
}

export default function LoginForm(props: Props) {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const navigate = useNavigate()

    function handleSubmit(event: FormEvent) {
        event.preventDefault()
        props.onLogin(username, password)
        navigate("/")
    }

return (<>
        <form onSubmit={handleSubmit}>
            <fieldset>
                <legend style={{marginBottom: '20px', fontWeight: 'bold', fontSize: '28px'}}>Login</legend>
                <ControlledInput
                    label="Username"
                    type="text"
                    value={username}
                    id="username"
                    onChange={setUsername}
                />
                <ControlledInput
                    label="Password"
                    type="password"
                    value={password}
                    id="password"
                    onChange={setPassword}
                />

                <div>
                    <Button sx={{mt: 1, mr: 1}} variant="outlined" disableElevation
                            onClick={() => navigate("/")}> Cancel</Button>

                    <Button sx={{mt: 1, mr: 1}} type="submit" variant="contained" className="button-right">
                        Login
                    </Button>
                </div>
            </fieldset>
        </form>
    </>
)
}
