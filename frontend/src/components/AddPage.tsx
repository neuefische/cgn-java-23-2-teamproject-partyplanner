import {PartyWithoutId} from "../models.ts";
import InputForm from "./InputForm.tsx";

type Props = {
    onAddParty: (data: PartyWithoutId) => void;
}
export default function AddPage(props: Props) {
    const newParty: PartyWithoutId = {theme: "", date: "", location: ""}

    return <InputForm onSubmitParty={props.onAddParty} party={newParty} legend={"Add new Party"} backUrl="/"/>
}
