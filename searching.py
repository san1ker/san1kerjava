graph = {
    'A': ['B'],
    'B': ['A', 'C', 'H'],
    'C': ['B', 'D'],
    'D': ['C', 'E', 'G'],
    'E': ['D', 'F'],
    'F': ['E'],
    'G': ['C'],
    'H': ['B', 'I', 'J', 'M'],
    'I': ['H'],
    'J': ['H', 'K'],
    'K': ['J', 'L'],
    'L': ['K'],
    'M': ['H']
}
#Iteration
def bfs_iter(graph, start_node):
    visit = []
    queue = []

    queue.append(start_node)

    while queue:
        node = queue.pop(0)
        if node not in visit:
            visit.append(node)
            queue.extend(graph[node])
    return visit
print (bfs_iter(graph, 'A'))

def dfs_iter(graph, start_node):
    visit = []
    stack = []

    stack.append(start_node)

    while stack:
        node = stack.pop()
        if node not in visit:
            visit.append(node)
            stack.extend(reversed(graph[node]))
    return visit

print (dfs_iter(graph, 'A'))

#Recursive
def dfs_recur(graph, start, visited = []):
    visited.append(start)

    for node in graph:
        if node not in visited:
            dfs_recur(graph, node, visited)

    return visited
print (dfs_recur(graph, 'A', []))

#https://juhee-maeng.tistory.com/25
#Path

