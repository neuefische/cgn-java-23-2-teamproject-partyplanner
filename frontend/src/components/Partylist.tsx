import {Party} from "../models.ts";
import PartyItem from "./PartyItem.tsx";


type Props= {
    parties: Party[]
}
export default function Partylist(props: Props) {
    return (
        <ul>
            {props.parties.map(party => <PartyItem party={party} key={party.id}/>)}
        </ul>
    );
}

