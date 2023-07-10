import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button } from '@mui/material';
import { Party } from "../models.ts";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

type Props = {
    onDeleteParty: (id: string) => void
}

export default function PartyDetail(props: Props) {

    const [party, setParty] = useState<Party>();
    const [randomImage, setRandomImage] = useState<string>();

    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`/api/parties/${params.id}`)
            .then(response => response.data)
            .then(data => setParty(data))
            .catch(console.error);
    }, [params.id]);

    useEffect(() => {
        axios.get(`/api/parties/randomCatImage`)
            .then(response => response.data.urls.small)
            .then(data => setRandomImage(data))
            .catch(console.error);
    }, []);


    if (!party) {
        return <>No Party</>
    }


    return (
        <Card sx={{ maxWidth: 345 }} style={{ display: "flex", flexDirection: "column" }}>
            {randomImage && (
                <CardMedia
                    component="img"
                    height="200"
                    image={randomImage}
                    alt="random cat image"
                />
            )}
            <CardContent style={{ display: "flex", gap: "2rem" }}>
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
                sx={{ m: 1 }}
                size="small"
                color="primary"
                variant="contained"
                onClick={() => navigate(`/${party.id}/edit`)}>Edit</Button>
            <Button
                sx={{ m: 1 }}
                size="small"
                color="error"
                variant="outlined"
                onClick={() => props.onDeleteParty(party.id)}>Delete</Button>
            <Button
                sx={{ m: 1 }}
                variant="outlined"
                disableElevation
                onClick={() => navigate(`/`)}>Back to List</Button>
        </Card>
    );
}
