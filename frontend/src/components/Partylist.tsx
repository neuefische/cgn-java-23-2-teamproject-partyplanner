import {Party} from "../models.ts";
import PartyItem from "./PartyItem.tsx";

type Props= {
    parties: Party[]
}
export default function Partylist(props: Props) {
    return (
        <ul>
            <div>
                <h3>Theme</h3>
                <h3>Date</h3>
                <h3>Location</h3>

            </div>
            {props.parties.map(party => <PartyItem party={party} key={party.id}/>)}
        </ul>
    );
}

