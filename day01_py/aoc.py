from os import environ
from queue import PriorityQueue

def getSolutionPart1(input):
    best = 0
    for s in input.split("\n\n"):
        current = 0
        points = s.split("\n")
        for p in points:
            current += int(p)
        if current > best: best = current 
    return best

def getSolutionPart2(input):
    q = PriorityQueue()
    q.put(0)
    q.put(0)
    q.put(0)
    for s in input.split("\n\n"):
        current = 0
        points = s.split("\n")
        for p in points:
            current += int(p)
        temp = q.get()
        q.put(current) if current > temp else q.put(temp)   
    return q.get()+q.get()+q.get()

with open('input.txt') as f:
    input = f.read()

print('Python')
part = environ.get('part')

if part == 'part2':
    print(getSolutionPart2(input))
else:
    print(getSolutionPart1(input))