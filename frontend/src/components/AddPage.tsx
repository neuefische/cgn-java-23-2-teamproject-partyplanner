import {DTOParty} from "../models.ts";
import InputForm from "./InputForm.tsx";

type Props = {
    onAddParty: (data: DTOParty) => void;
}
export default function AddPage(props: Props) {
    const newParty: DTOParty = {theme: "", date: "", location: ""}

    return <InputForm onSubmitParty={props.onAddParty} party={newParty} legend={"Add new Party"} backUrl="/"/>
}
