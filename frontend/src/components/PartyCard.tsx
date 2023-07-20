import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Party } from "../models.ts";
import { useState} from "react";
import { useNavigate} from "react-router-dom";
import EditIcon from '@mui/icons-material/Edit';
import Box from '@mui/material/Box';
import Typography from "@mui/material/Typography";
import Sheet from '@mui/joy/Sheet';

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
    user?: string;
    userId?: string;
};

export default function PartyCard(props: Props) {
    const [expanded, setExpanded] = useState(false);
    const [filterLocation, setFilterLocation] = useState('');

    const isAuthenticated = props.user !== undefined && props.user !== "anonymousUser";
    const navigate = useNavigate();

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleShareClick = (id: string) => {
        const url = window.location.href + id;
        const shareUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(url)}`;
        window.open(shareUrl, '_blank');
    };

    const handleFilterLocationChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilterLocation(event.target.value);
    };

    const filteredParties = props.parties.filter(party => {
        const partyLocation = party.location.toLowerCase();
        const filterLocationLower = filterLocation.toLowerCase();
        return partyLocation.includes(filterLocationLower);
    });

    return (
        <>
            <Sheet
                variant="outlined"
                sx={{ p: 2, borderRadius: 'sm', width: 345 }}
            >
                <Typography
                    id="filter-location"
                    sx={{
                        fontSize: '14px',
                        color: 'text.secondary',
                        mt: 2,
                    }}
                >
                    Parties at your location
                </Typography>
                <Box role="group" aria-labelledby="filter-location">
                    <input className="input"
                        type="text"
                        value={filterLocation}
                        onChange={handleFilterLocationChange}
                        placeholder="Enter location"
                    />
                </Box>
            </Sheet>
            {filteredParties.map((party) => (
                <Card key={party.id} sx={{ maxWidth: 345, flexDirection: 'column' }}>
                    <Box sx={{ ml: 2, justifyContent: 'flex-start'}}>
                        <Avatar aria-label="emoji"><span>🥳</span></Avatar>
                        <Typography sx={{ mt: 2, ml: 1, color: 'rgb(44, 161, 173)', fontSize: '30px', fontFamily: 'Belanosima'}}>
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
                        {isAuthenticated && props.userId === party.userId && <FavoriteIcon />}
                        <IconButton aria-label="share party" onClick={() => handleShareClick(party.id)}>
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
                            <Typography paragraph>Guestlist: coming soon</Typography>
                        </CardContent>
                    </Collapse>
                </Card>
            ))}
        </>
    )
}
