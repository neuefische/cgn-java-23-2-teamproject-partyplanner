import {Party} from "../models.ts";
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import {CardActionArea} from '@mui/material';

type Props = {
    party: Party
}
export default function PartyItem(props: Props) {

    const dateString = new Date(props.party.date).toLocaleDateString("de-DE");

    return (
        <Card>
            <CardActionArea>
                <CardContent>
                    <Typography variant="overline" component="div">
                        {props.party.theme}
                    </Typography>
                    <Typography variant="overline" color="text.secondary" component="div">
                        {dateString}
                    </Typography>
                    <Typography variant="overline" color="text.secondary" component="div">
                        {props.party.location}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

