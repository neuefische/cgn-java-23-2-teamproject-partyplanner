import {Party} from "../models.ts";
import './PartyItem.css'

type Props= {
    party: Party
}
export default function PartyItem(props: Props) {

    return (
        <li>
            <h2>{props.party.theme}</h2>
            <h3>{props.party.date}</h3>
            <h3>{props.party.location}</h3>
            <h3>{props.party.guests.length}</h3>
        </li>

    );
}

