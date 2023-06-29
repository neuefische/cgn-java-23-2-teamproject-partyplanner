export type Party= {
    id: string,
    date: string,
    location: string,
    guests: Guest[],
    theme: string,
}


export type Guest= {
    id: string,
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