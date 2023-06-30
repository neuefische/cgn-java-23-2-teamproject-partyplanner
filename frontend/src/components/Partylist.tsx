import {Party} from "../models.ts";
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

type Props = {
    parties: Party[]
}
export default function Partylist(props: Props) {
    return (
        <Paper sx={{width: '100%', overflow: 'hidden'}}>
            <TableContainer sx={{maxHeight: 440}}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            <TableCell style={{fontWeight: "bold"}}>
                                Theme
                            </TableCell>
                            <TableCell style={{fontWeight: "bold"}}>
                                Date
                            </TableCell>
                            <TableCell style={{fontWeight: "bold"}}>
                                Location
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.parties.map(party => {
                            return (
                                <TableRow hover role="checkbox" tabIndex={-1} key={party.id}>
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
    );
}

