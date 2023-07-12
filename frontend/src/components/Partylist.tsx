import {Party} from "../models.ts";
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import {useNavigate} from "react-router-dom";

type Props = {
    parties: Party[]
}
export default function Partylist(props: Props) {
    const navigate = useNavigate();

    return (
<><h2 style={{marginTop: '10px'}}> Partylist</h2>
    <Paper sx={{
        width: '100%',
        overflow: 'hidden',
        backgroundColor: '#bdd6d9',
        border: '0px',
        margin: '0px 0px 10px 0px',
        boxShadow: 'none'
    }}>

        <TableContainer sx={{margin: '0px'}}>
            <Table stickyHeader aria-label="sticky table">
                <TableHead sx={{width: '100%'}}>
                    <TableRow>
                        <TableCell style={{fontWeight: "bold", backgroundColor: '#32abb8', color: 'white'}}>
                            Theme 🎈
                        </TableCell>
                        <TableCell style={{fontWeight: "bold", backgroundColor: '#32abb8', color: 'white'}}>
                            Date 🗓️
                        </TableCell>
                        <TableCell style={{fontWeight: "bold", backgroundColor: '#32abb8', color: 'white'}}>
                            Location 📍
                        </TableCell>
                    </TableRow>
                </TableHead>
                    <TableBody>
                        {props.parties.map(party => {
                            return (
                                <TableRow hover role="checkbox" tabIndex={-1} key={party.id} onClick={() => navigate(`/${party.id}`)}>
                                    <TableCell> {party.theme}</TableCell>
                                    <TableCell>{new Date(party.date).toLocaleDateString("de-DE")} </TableCell>
                                    <TableCell>{party.location} </TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
</>
    );
}
