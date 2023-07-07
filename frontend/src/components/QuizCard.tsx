import {useState} from "react";
import {Quiz} from "../models.ts";
import {Box, CardActions} from "@mui/material";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";

type Props = {
    quiz: Quiz;
}
export default function QuizCard(props: Props) {
    const [answer, setAnswer] = useState<boolean>(false);

    return <Box sx={{ minWidth: "100%"}}>
        <Card variant="outlined" sx={{width: "100%", display: "flex", flexDirection: "column"}}>
                <CardContent sx={{display: "flex", flexDirection: "column"}}>
                    <Typography sx={{ fontSize: 10 }} color="text.secondary" gutterBottom>
                        Quiz Generator
                    </Typography>
                    <Typography variant="h5" sx={{ fontSize: 20, m: 0 }}>
                        {props.quiz.question}
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="large" sx={{margin: "auto"}} onClick={() => setAnswer(!answer)}>{answer ? props.quiz.answer : "Show Answer"}</Button>
                </CardActions>
        </Card>
    </Box>
}