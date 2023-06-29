import {Diet, Guest, Party} from "./models.ts";


const guests: Guest[]= [
    {
    id: "1",
    name: "Pia",
    rsvp: false,
    diet: 0,
    },
    {
        id: "2",
        name: "Canan",
        rsvp: true,
        diet: Diet.VEGETARIAN
    },
    {
        id: "3",
        name: "Anton",
        rsvp: true,
        diet: Diet.OMNIVORE
    },
    {
        id: "4",
        name: "Gökhan",
        rsvp: true,
        diet: Diet.ANIMAL
    },
]

export const parties: Party[]= [{
    id: "1",
    date: new Date(),
    location: "Terry's Home",
    guests: guests,
    theme: "Dog-Bday",
    },
    {
        id: "2",
        date: new Date(),
        location: "Olga's Home",
        guests: guests,
        theme: "Cat-Bday",
    },
    {
        id: "3",
        date: new Date(),
        location: "Henry's Home",
        guests: guests,
        theme: "Bunny-Bday",
    }
]


