import {Party} from "../models.ts";

type Props= {
    party: Party
}
export default function PartyItem(props: Props) {

    const dateString = new Date(props.party.date).toLocaleDateString("de-DE");

    return (
        <li>
            <h3>{props.party.theme}</h3>
            <h3>{dateString}</h3>
            <h3>{props.party.location}</h3>
        </li>

    );
}

