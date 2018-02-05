# search.py
# ---------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# For more info, see http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html

"""
In search.py, you will implement generic search algorithms which are called
by Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
  """
  This class outlines the structure of a search problem, but doesn't implement
  any of the methods (in object-oriented terminology: an abstract class).

  You do not need to change anything in this class, ever.
  """

  def startingState(self):
    """
    Returns the start state for the search problem
    """
    util.raiseNotDefined()

  def isGoal(self, state): #isGoal -> isGoal
    """
    state: Search state

    Returns True if and only if the state is a valid goal state
    """
    util.raiseNotDefined()

  def successorStates(self, state): #successorStates -> successorsOf
    """
    state: Search state
     For a given state, this should return a list of triples,
     (successor, action, stepCost), where 'successor' is a
     successor to the current state, 'action' is the action
     required to get there, and 'stepCost' is the incremental
     cost of expanding to that successor
    """
    util.raiseNotDefined()

  def actionsCost(self, actions): #actionsCost -> actionsCost
    """
      actions: A list of actions to take

     This method returns the total cost of a particular sequence of actions.  The sequence must
     be composed of legal moves
    """
    util.raiseNotDefined()


def tinyMazeSearch(problem):
  """
  Returns a sequence of moves that solves tinyMaze.  For any other
  maze, the sequence of moves will be incorrect, so only use this for tinyMaze
  """
  from game import Directions
  s = Directions.SOUTH
  w = Directions.WEST
  return  [s,s,w,s,w,w,s,w]

def depthFirstSearch(problem):
  """
  Search the deepest nodes in the search tree first [p 85].

  Your search algorithm needs to return a list of actions that reaches
  the goal.  Make sure to implement a graph search algorithm [Fig. 3.7].

  To get started, you might want to try some of these simple commands to
  understand the search problem that is being passed in:

  print "Start:", problem.startingState()
  print "Is the start a goal?", problem.isGoal(problem.startingState())
  print "Start's successors:", problem.successorStates(problem.startingState())
  """

  # Helper function for frontier iteration
  # Check to see if a space is in the frontier
  def _inFrontier(frontier, state):
      for tempState in frontier.list:
          if tempState == state:
              return True
      return False

  # Initialization of bookkeeping data structures / variables
  startSpace = problem.startingState()
  if problem.isGoal(startSpace): return []
  visited = []
  frontier = util.Stack()
  # frontier contains (state, level) tuples
  map(lambda x: frontier.push((x, 1)), problem.successorStates(startSpace))
  path = []

  # Main loop of depthFirstSearch
  while (not frontier.isEmpty()):
      node = frontier.pop()
      level = node[1]
      # We only want the path to contain actions up until node.
      # This is useful if frontier pops a node that
      #     is at a level <= the level of the previous node
      path = path[:(level-1)]
      visited.append(node[0][0])
      path.append(node[0][1])
      # Loops through every valid state adjacent to node (current state)
      for child in problem.successorStates(node[0][0]):
          # Further confirms child's validity by checking if
          #     it has been visited or is in the current frontier
          if (child[0] not in visited and not _inFrontier(frontier, child)):
              if(problem.isGoal(child[0])):
                  # Success!
                  path.append(child[1])
                  return path
              # child is potentially a state on the way to goalState
              frontier.push((child, level+1))


def breadthFirstSearch(problem):
  "Search the shallowest nodes in the search tree first. [p 81]"
  # Checks if the space is in the Frontier
  def _inFrontier(frontier, space):
      for node in frontier.list:
          if node[0] == space: return True
      return False

  # Initialization of bookkeeping data structures / variables
  startSpace = problem.startingState()
  if problem.isGoal(startSpace): return []
  visited = []
  frontier = util.Queue()
  # frontier contains (state, path) tuples
  frontier.push((startSpace, []))
  # Main loop of depthFirstSearch
  while (not frontier.isEmpty()):
      node = frontier.pop()
      visited.append(node[0])
      # Loops through every valid state adjacent to node (current state)
      for child in problem.successorStates(node[0]):
          # Further confirms child's validity by checking if
          #     it has been visited or is in the current frontier
          if (child[0] not in visited and not _inFrontier(frontier, child[0])):
              path = list(node[1])
              path.append(child[1])
              if(problem.isGoal(child[0])):
                  # Success!
                  return path
              # child is potentially a state on the way to goalState
              frontier.push((child[0], path))


def uniformCostSearch(problem):
    "Search the node of least total cost first. "
    # Helper function for frontier iteration
    # Check to see if a space is in the frontier
    def _inFrontier(frontier, space):
        for tempSpace in frontier.heap:
            if tempSpace[1] == space: return True
        return False
    # Helper function for getting rid of a PriorityQueue element
    def _chop(frontier, space):
        for i, tempSpace in frontier:
            if tempSpace[1] == space:
                frontier.pop(i)
                break
    # Initialization of bookkeeping data structures / variables
    # startSpace
    startSpace = problem.startingState()
    # _visited
    visited = []
    # Frontier
    frontier = util.PriorityQueue()
    # actionMap
    actionMap = {}
    # parentMap
    parentMap = {}
    # costMap
    costMap = {}
    # add startSpace to costMap
    costMap[startSpace] = 0
    # is startSpace goalSpace?
    if problem.isGoal(startSpace): return []
    # Frontier.push(startSpace, 0)
    frontier.push(startSpace, 0)
    # while (not Frontier.isEmpty())
    while (not frontier.isEmpty()):
        # node = Frontier.pop()
        space = frontier.pop()
        # visited.append(node)
        visited.append(space)
        # is node goalState?
        if problem.isGoal(space):
            # actions = []
            actions = []
            # while space != startState:
            while space != startSpace:
                # actions.insert(0, actionMap[node])
                actions.insert(0, actionMap[space])
                # space = parentMap[space]
                space = parentMap[space]
            # return actions
            return actions
        # for child in problem.successorStates(node)
        for childState in problem.successorStates(space):
            # child direction
            direction = childState[1]
            # child space
            childSpace = childState[0]
            # child cost
            childCost = childState[2]
            # cost t get to this space
            pathCost = costMap[space] + childCost
            # if not child in visited
            if not childSpace in visited:
                # if not child in frontier
                if not _inFrontier(frontier, childSpace):
                    # push child onto Frontier
                    frontier.push(childSpace, pathCost)
                    # set actionMap[child]
                    actionMap[childSpace] = direction
                    # set parentMap[child] = node
                    parentMap[childSpace] = space
                    # set costMap[child] = costMap[node] + edgeCost
                    costMap[childSpace] = pathCost
                # if pathCost is a shorter path to childSpace
                elif costMap[childSpace] > pathCost:
                    # get rid of that space from the PQ
                    _chop(frontier, childSpace)
                    # push child onto Frontier
                    frontier.push(childSpace, pathCost)
                    # set actionMap[child]
                    actionMap[childSpace] = direction
                    # set parentMap[child] = node
                    parentMap[childSpace] = space
                    # set costMap[child] = costMap[node] + edgeCost
                    costMap[childSpace] = pathCost



def nullHeuristic(state, problem=None):
  """
  A heuristic function estimates the cost from the current state to the nearest
  goal in the provided SearchProblem.  This heuristic is trivial.
  """
  return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    "Search the node that has the lowest combined cost and heuristic first."
    # Helper function for frontier iteration
    # Check to see if a space is in the frontier
    def _inFrontier(frontier, space):
        for tempSpace in frontier.heap:
            if tempSpace[1][0] == space: return True
        return False
    # Helper function for getting rid of a PriorityQueue element
    def _chop(frontier, space):
        for tempSpace in frontier.heap:
            if tempSpace[1][0] == space:
                frontier.heap.pop(frontier.heap.index(tempSpace))
                break
    # Helper function for dtermining pathCost of a space in the frontier
    def _stateCost(frontier, space):
        for state in frontier.heap:
            if state[1][0] == space:
                return state[1][1]
    # Initialization of bookkeeping data structures / variables
    # startSpace
    startState = problem.startingState()
    # _visited
    visited = []
    # Frontier
    frontier = util.PriorityQueue()
    # is startSpace goalSpace?
    if problem.isGoal(startState): return []
    # node = (state, cost, heurCost, parent, action)
    frontier.push((startState, 0, heuristic(startState, problem), None, None), 0+heuristic(startState, problem))
    # while (not Frontier.isEmpty())
    while (not frontier.isEmpty()):
        # node = Frontier.pop()
        state = frontier.pop()
        # visited.append(node)
        visited.append(state[0])
        # is node goalState?
        if problem.isGoal(state[0]):
            # actions = []
            actions = []
            # while space != startState:
            while state[0] != startState:
                # actions.insert(0, actionMap[node])
                actions.insert(0, state[4])
                # space = parentMap[space]
                state = state[3]
            # return actions
            return actions
        # for child in problem.successorStates(node)
        for childState in problem.successorStates(state[0]):
            # child direction
            direction = childState[1]
            # child space
            childSpace = childState[0]
            # child cost
            childCost = childState[2]
            # cost to get to this space
            pathCost = state[1] + childCost
            # heuristic path cost
            heurPathCost = pathCost + heuristic(childSpace, problem)
            # if not child in visited
            if not childSpace in visited:
                # if not child in frontier
                if not _inFrontier(frontier, childSpace):
                    # push child onto Frontier
                    frontier.push(
                        (childSpace, pathCost, heuristic(childSpace, problem), state, direction),
                        heurPathCost
                    )
                # if pathCost is a shorter path to childSpace
            elif _stateCost(frontier, childSpace) > pathCost:
                    # get rid of that space from the PQ
                    _chop(frontier, childSpace)
                    # push child onto Frontier
                    frontier.push(
                        (childSpace, pathCost, heuristic(childSpace, problem), state, direction),
                        heurPathCost
                    )


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
