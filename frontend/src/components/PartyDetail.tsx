import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import {Button} from '@mui/material';
import {Party} from "../models.ts";
import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

type Props = {
    onDeleteParty: (id: string) => void
}
export default function PartyDetail(props: Props) {

    const [party, setParty] = useState<Party>();

    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`/api/parties/${params.id}`)
            .then(response => response.data)
            .catch(console.error)
            .then(data => setParty(data))
    }, [params.id])



    if(typeof party === "undefined"){
        return <>No Party</>
    }


    return <Card sx={{ maxWidth: 345 }} style={{display:"flex", flexDirection: "column"}}>
            <CardMedia
                component="img"
                height="140"
                image="https://i.etsystatic.com/5157460/r/il/aff006/2344085974/il_1588xN.2344085974_czdu.jpg"
                alt="green iguana"
            />
            <CardContent style={{display:"flex", gap: "2rem"}}>
                <Typography variant="overline" component="div">
                    {party.theme}
                </Typography>
                <Typography variant="overline" component="div">
                    {new Date(party.date).toLocaleDateString("de-DE")}
                </Typography>
                <Typography variant="overline" component="div">
                    {party.location}
                </Typography>
            </CardContent>
        <Button
            sx={{m: 1}}
            size="small"
            color="primary"
            variant="contained"
            onClick={() => navigate(`/${party.id}/edit`)}>Edit</Button>
        <Button
            sx={{m: 1}}
            size="small"
            color="error"
            variant="outlined"
            onClick={() => props.onDeleteParty(party.id)}>Delete</Button>
        <Button
            sx={{m: 1}}
            variant="outlined"
            disableElevation
            onClick={() => navigate(`/`)}>Back to List</Button>
    </Card>
}