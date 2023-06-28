export type Party= {
    id: number,
    date: Date,
    location: string,
    guests: Guest[],
    theme: string,
}


export type Guest= {
    id: number,
    name: string,
    rsvp: boolean,
    diet: Diet,
}

export enum Diet {
    VEGAN,
    VEGETARIAN,
    OMNIVORE,
    ANIMAL,
}