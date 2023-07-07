import {TextField} from "@mui/material";
import React from "react";

type Props = {
    label: string,
    type: string,
    value: string,
    id: string,
    onChange: (value: string) => void
}

export default function ControlledInput(props: Props) {
    return <TextField
        id={props.label}
        label={props.label}
        value={props.value}
        type={props.type}
        variant="filled"
        color="secondary"
        required
        style={{marginLeft: 20, marginRight: 20, paddingTop: 20}}
        onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            props.onChange(event.target.value);
        }}
    />
}
