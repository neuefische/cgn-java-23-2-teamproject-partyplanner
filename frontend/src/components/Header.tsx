import './Header.css'
import Button from "@mui/material/Button";
import {useNavigate} from "react-router-dom";

type Props = {
    user?: string
}
export default function Header(props: Props) {
    const navigate = useNavigate();

    const isAuthenticated = props.user !== undefined && props.user !== "anonymousUser";

    return (
        <header>
            <nav style={{
                width: "100%",
                height: "33px",
                background: "black",
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center"
            }}>
                <b style={{
                    fontSize: "20px", color: "white",
                    padding: "10px",
                    background: "url https://2.bp.blogspot.com/-LHGHDFttakA/WDZeTrF-0CI/AAAAAAADxss/WwV9PKUJ3g0cut9k2uaBG1k2KHstD1dpwCLcB/s1600/AF004206_02.gif"
                }}>Paw Palace Parties</b>
                {!isAuthenticated ?
                    <Button sx={{m: "20px", p: "10px"}} className="button-login" variant="contained" color="inherit"
                            disableElevation
                            onClick={() => navigate("/login")}>
                        Login
                    </Button> : <p style={{color: "white", margin: "50px"}}>{props.user}</p>}
            </nav>
        </header>
    )
}
