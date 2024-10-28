import random
def starting_room():
    number_of_doors=3
    treasure_room_door=random.randint(1,number_of_doors)
    print("You are in a room with 3 doors.")
    while True:
        choice = int(input("Which door (1 - 3) do you choose? "))
        if choice == treasure_room_door:
            treasure_room() 
            break   
        else:
            random_event = random.randint(0, 1)
            if random_event == 0:
                item_room()
            else:
                if not monster_room(): 
                    break
def treasure_room():
    print("You have found the ultimate treasure chest! You win the game!")

room_items= ["bow", "armour", "boomerang", "shield", "sword"]
def item_room():
    global room_items 
    random_item=random.randint(0,len(room_items)-1)
    print(f"You found {room_items[random_item]}, you decide to pick it up!")
    return None
def fight_monster():
    print("You are fighting the monster...")
    random_fight=random.randint(0,10)
    if random_fight>3:
        print("You defeated the monster! You win!")
        return True
    else:
        print("The monster defeated you. You lose the game.")
        return False
def monster_room():
    print("You have entered a room with a monster!")
    while True:
        choice=input("Do you choose to fight or flee? ")
        if choice=="fight":
            return fight_monster()
        elif choice=="flee":
            print("You fled back to the starting room.")
            return True
        else:
            print("Invalid choice. Please choose to fight or flee.")

starting_room()