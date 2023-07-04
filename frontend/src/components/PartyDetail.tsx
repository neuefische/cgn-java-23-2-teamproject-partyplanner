import {Party} from "../models.ts";
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';

type Props = {
    party: Party
}

export default function PartyDetail(props: Props) {

    return <Card sx={{ maxWidth: 345 }}>
        <CardActionArea>
            <CardMedia
                component="img"
                height="140"
                image="/static/images/cards/contemplative-reptile.jpg"
                alt="green iguana"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">{props.party.theme}</Typography>
                <Typography variant="body2" color="text.secondary">{props.party.date}</Typography>
                <Typography variant="body2" color="text.secondary">{props.party.location}</Typography>
            </CardContent>
        </CardActionArea>
        <CardActions>
            <Button size="small" color="primary">
                Edit
            </Button>
        </CardActions>
    </Card>
}