export type Party= {
    id: string,
    date: string,
    location: string,
    theme: string,
}

export type PartyWithoutId = {
    date: string,
    location: string,
    theme: string,
}

export type Quiz = {
    id: string,
    question: string,
    answers: {
        answerText: string
        rightAnswer: boolean
    }[]
}
