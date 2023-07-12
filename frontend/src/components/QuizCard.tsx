import {Quiz} from "../models.ts";
import {Box, CardActions} from "@mui/material";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import {useState} from "react";

type Props = {
    quiz: Quiz;
    onSolveQuiz: (id: string) => void;
}
export default function QuizCard(props: Props) {
    const [solved, setSolved] = useState<boolean>(false);


    return <Box sx={{width: '100%', overflow: 'hidden'}}>
        <Card variant="outlined" sx={{width: "100%", display: "flex", flexDirection: "column"}}>
                <CardContent sx={{display: "flex", flexDirection: "column"}}>
                    <Typography sx={{ fontSize: 10 }} color="text.secondary" gutterBottom>
                        Quiz Generator
                    </Typography>
                    <Typography variant="h5" sx={{ fontSize: 20, m: 0 }}>
                        {props.quiz.question}
                    </Typography>
                </CardContent>
            <CardActions sx={{display: "flex", flexWrap: "wrap"}}>
                {props.quiz.answers.map((answer, index) =>
                    <Button
                        key={answer.answerText + index}
                        color={!solved ? "secondary" : solved && answer.rightAnswer ? "success" : "error"}
                        onClick={() => {
                        props.onSolveQuiz(props.quiz.id);
                        setSolved(true);
                    }}>{answer.answerText}</Button>)}
            </CardActions>
        </Card>
    </Box>
}