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
        size="small"
        required
        style={{marginLeft: 5, marginRight: 5, paddingTop: 20, width: "95%"}}
        onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            props.onChange(event.target.value);
        }}
    />
}
