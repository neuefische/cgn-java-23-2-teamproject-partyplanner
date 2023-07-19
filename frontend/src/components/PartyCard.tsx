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
import { useState } from "react";
import { useNavigate } from "react-router-dom";
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
    user?: string
    userId?: string
};

export default function PartyCard(props: Props) {
    const [expanded, setExpanded] = useState(false);
    const navigate = useNavigate();
    const isAuthenticated = props.user !== undefined && props.user !== "anonymousUser";

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleShareClick = () => {
        const url = window.location.href;
        const shareUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(url)}`;
        window.open(shareUrl, '_blank');
    };

    return (
        <>
            {props.parties.map((party) => (
                <Card key={party.id} sx={{ maxWidth: 345, flexDirection: 'column' }}>
                    <Box sx={{ ml: 2, mr: 6, justifyContent: 'space-between' }}>
                        <Avatar aria-label="user">PP</Avatar>
                        <Typography sx={{ mt: 2, color: 'rgb(44, 161, 173)', fontSize: '35px' }}>
                            {party.theme}
                        </Typography>
                    </Box>
                    <CardContent sx={{ ml: 1, display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                            <Typography sx={{ fontSize: '24px', mr: 4 }}>🗓</Typography>
                            <Typography sx={{ mr: 1, minWidth: '80px' }}>Date:</Typography>
                            <Typography>
                                {new Date(party.date).toLocaleDateString("de-DE")}
                            </Typography>
                        </Box>
                        <Box sx={{ display: 'flex', alignItems: 'center' }}>
                            <Typography sx={{ fontSize: '24px', mr: 4 }}>📍</Typography>
                            <Typography sx={{ mr: 1, minWidth: '80px' }}>Location:</Typography>
                            <Typography>{party.location}</Typography>
                        </Box>
                    </CardContent>

                    <CardActions disableSpacing>
                        {isAuthenticated && props.userId === party.userId && <>
                            <IconButton
                                aria-label="add to favorites"
                            >
                                <FavoriteIcon />
                            </IconButton>
                            </>
                        }
                        <IconButton aria-label="share party" onClick={handleShareClick}>
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
