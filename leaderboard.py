from datetime import datetime, timedelta

def init_leaderboard()->dict[str,timedelta]:
    leaderboard : dict[str,timedelta] = {}
    return leaderboard

def add_player(leaderboard: dict[str,timedelta],player_name: str)->bool:
    if player_name in leaderboard:
        return False
    else:
        leaderboard[player_name] = None
        return True
    
def  add_run(leaderboard: dict[str,timedelta], player_name: str, time: timedelta)->int:
    zero_time = timedelta(0) 
    if time<zero_time:
        return 1
    elif player_name not in leaderboard:
        return 2
    else:
        current_time = leaderboard[player_name]
        if current_time is None or time < current_time:
            leaderboard[player_name] = time  
            return 0
        return 0

def clear_score(leaderboard: dict[str,timedelta], player_name: str)->bool:
    if player_name in leaderboard:
        leaderboard[player_name]=None
        return True
    else:
        return False
    
def display_leaderboard(leaderboard: dict[str, timedelta], n: int = 3) -> None:
    leaderboard_copy = leaderboard.copy()
    
    valid_leaderboard = {key: value for key, value in leaderboard_copy.items() if value is not None}

    if not valid_leaderboard:    
        print("Leaderboard is empty")
    else:
        reverse_order = dict(sorted(valid_leaderboard.items(), key=lambda x: x[1]))
        count = 0
        for key, value in reverse_order.items():
            if count == n:
                break
            print((count + 1), key, f"{value}", sep="\t")  
            count += 1    

def calculate_average_time(leaderboard: dict[str,timedelta])->tuple[bool, timedelta]:
    valid_times = [time for time in leaderboard.values() if time is not None]
    if not valid_times:
        return(False, None)
    else:
        average_time = sum(valid_times, timedelta()) / len(valid_times)
        return (True,average_time) 

leaderboard = init_leaderboard()
add_player(leaderboard, "Alice")
add_player(leaderboard, "Bob")
assert add_run(leaderboard, "Alice", timedelta(seconds=57)) == 0
assert leaderboard == {"Alice": timedelta(seconds=57), "Bob": None}
print(add_run(leaderboard, "Alice", timedelta(seconds=67))) 
assert leaderboard == {"Alice": timedelta(seconds=57), "Bob": None}
assert add_run(leaderboard, "Alice", timedelta(seconds=-1)) == 1
assert leaderboard == {"Alice": timedelta(seconds=57), "Bob": None}
assert add_run(leaderboard, "Charlie", timedelta(seconds=-1)) == 1
assert leaderboard == {"Alice": timedelta(seconds=57), "Bob": None}
assert add_run(leaderboard, "Charlie", timedelta(seconds=52)) == 2
assert leaderboard == {"Alice": timedelta(seconds=57), "Bob": None}
add_run(leaderboard, "Alice", timedelta(seconds=52))
assert leaderboard == {"Alice": timedelta(seconds=52), "Bob": None}