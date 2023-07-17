import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Party } from "../models.ts";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import EditIcon from '@mui/icons-material/Edit';
import Box from '@mui/material/Box';

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

type Props = {
    parties: Party[];
};

export default function PartyCard(props: Props) {
    const [expanded, setExpanded] = useState(false);
    const navigate = useNavigate();

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    return (
        <>
            {props.parties.map((party) => (
                <Card key={party.id} sx={{ maxWidth: "450px", width: "100%", flexDirection: 'column'}}>
                    <Box sx={{ justifyContent: 'space-around' }}>
                        <Avatar aria-label="user">PP</Avatar>
                        <Typography variant="h6" sx={{ mt: 2}}>
                            {party.theme}
                        </Typography>
                    </Box>
                    <CardContent sx={{ ml: 1, flexDirection: 'column'}}>
                        <Box>
                            <Typography sx={{ mr: 5}}> 🗓</Typography>
                            <Typography sx={{ mr: 20}}>Date: </Typography>
                            <Typography sx={{ flexGrow: 1 }}>{new Date(party.date).toLocaleDateString("de-DE")}</Typography>
                        </Box>
                        <Box>
                            <Typography sx={{ mr: 5}}>📍</Typography>
                            <Typography sx={{ mr: 17}}>Location:</Typography>
                            <Typography sx={{ flexGrow: 1 }}>{party.location} </Typography>
                        </Box>
                    </CardContent>
                    <CardActions disableSpacing>
                        <IconButton aria-label="add to favorites">
                            <FavoriteIcon />
                        </IconButton>
                        <IconButton aria-label="share party">
                            <ShareIcon />
                        </IconButton>
                        <IconButton aria-label="edit party" onClick={() => navigate(`/${party.id}`)}>
                            <EditIcon />
                        </IconButton>
                        <ExpandMore
                            expand={expanded}
                            onClick={handleExpandClick}
                            aria-expanded={expanded}
                            aria-label="show more"
                        >
                            <ExpandMoreIcon />
                        </ExpandMore>
                    </CardActions>
                    <Collapse in={expanded} timeout="auto" unmountOnExit>
                        <CardContent>
                            <Typography paragraph>Guestlist:</Typography>
                            <Typography paragraph>... Guests</Typography>
                        </CardContent>
                    </Collapse>
                </Card>
            ))}
        </>
    )
}
