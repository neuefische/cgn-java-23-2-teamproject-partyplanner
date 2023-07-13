import Button from "@mui/material/Button";
import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {TextField} from "@mui/material";



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
                <TextField
                    label="Username"
                    type="text"
                    value={username}
                    id="username"
                    onChange={(event) => setUsername(event.target.value)}
                />
                <TextField
                    label="Password"
                    type="password"
                    value={password}
                    id="password"
                    onChange={(event) => setPassword(event.target.value)}
                />

                <div>
                    <Button sx={{mt: 1, mr: 1, color: "rgb(44, 161, 173)", borderColor: "rgb(44, 161, 173)"}} variant="outlined" disableElevation
                            onClick={() => navigate("/")}> Cancel</Button>

                    <Button sx={{mt: 1, mr: 1, bgcolor: "rgb(44, 161, 173)"}} type="submit" variant="contained" className="button-right">
                        Login
                    </Button>
                </div>
            </fieldset>
        </form>
    </>
)
}
