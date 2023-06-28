import {Party} from "../models.ts";

type Props= {
    party: Party
}
export default function PartyItem(props: Props) {

    const dateString = props.party.date.toLocaleDateString("de-DE")

    return (
        <li>
            <h2>{props.party.theme}</h2>
            <h3>{dateString}</h3>
            <h3>{props.party.location}</h3>
            <h3>{props.party.guests.length}</h3>
        </li>

    );
}

