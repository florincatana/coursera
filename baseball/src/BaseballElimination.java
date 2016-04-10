import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BaseballElimination {
    
    private Map<String, Integer> teams = new LinkedHashMap<String, Integer>();
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private boolean[] isCalculated;
    private boolean[] isEliminated;
    private List<Set<String>> certificateOfElimination = new ArrayList<Set<String>>();
    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null)
            throw new NullPointerException("synsets filename");
        In in = new In(filename);
        int noOfTeams = in.readInt();
        List<List<String>> teamLines = Arrays.asList(in.readAllLines()).stream().map(s -> s.trim()).filter(
            s -> s.length() > 0).map(s -> s.replaceAll("\\s+", " ").split(" ")).filter(a -> a.length > 1).map(
                a -> Arrays.asList(a)).collect(java.util.stream.Collectors.toList());
        
        if (teamLines.size() != noOfTeams)
            throw new AssertionError("Invalid No Of Teams");
        
        isCalculated = new boolean[noOfTeams];
        isEliminated = new boolean[noOfTeams];
        
        w = new int[teamLines.size()];
        l = new int[teamLines.size()];
        r = new int[teamLines.size()];
        g = new int[teamLines.size()][teamLines.size()];
        for (int i = 0; i < teamLines.size(); i++) {
            teams.put(teamLines.get(i).get(0), Integer.valueOf(i));
            w[i] = Integer.parseInt(teamLines.get(i).get(1));
            l[i] = Integer.parseInt(teamLines.get(i).get(2));
            r[i] = Integer.parseInt(teamLines.get(i).get(3));
            for (int j = 0; j < noOfTeams; j++) {
                g[i][j] = Integer.parseInt(teamLines.get(i).get(j + 4));
            }
            certificateOfElimination.add(new LinkedHashSet<String>());
        }
        
        
        if (numberOfTeams() == 1) {
            isCalculated[0] = true;
        }
    }
    
    // number of teams
    public int numberOfTeams() {
        return teams.values().size();
    }
    
    // all teams
    public Iterable<String> teams() {
        return teams.keySet().stream().collect(java.util.stream.Collectors.toList());
    }
    
    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
        return w[teams.get(team)];
    }
    
    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
        return l[teams.get(team)];
    }
    
    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
        return r[teams.get(team)];
    }
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        return g[teams.get(team1)][teams.get(team2)];
    }
    
    
    // is given team eliminated?
    public boolean isEliminated(String team) {
        compute(team);
        return isEliminated[teams.get(team)];
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        compute(team);
        return isEliminated(team) ? certificateOfElimination.get(teams.get(team)) : null;
    }
    
    private void compute(String team) {
        validateTeam(team);
        int x = teams.get(team);
        if (isCalculated[x])
            return;
        isCalculated[x] = true;
        
        // now check if the calculation is trivial
        int maxWins = w[x] + r[x];
        List<String> otherTeams = teams.entrySet().stream().filter(e -> maxWins - w[e.getValue()] < 0).map(
            e -> e.getKey()).collect(java.util.stream.Collectors.toList());
        // lucky - trivial elimination
        if (otherTeams.size() > 0) {
            isEliminated[x] = true;
            certificateOfElimination.get(x).addAll(otherTeams);
        } else {
            // else we run fordFulkerson
            fordFulkerson(team);
        }
    }
    
    private void fordFulkerson(String team) {
        int numberOfTeams = numberOfTeams();
        int x = teams.get(team);
        int maxWins = w[x] + r[x];
        int noOfMatches = (numberOfTeams * (numberOfTeams - 1)) / 2;
        int noOfvertices = 2 + numberOfTeams + noOfMatches;
        int matchesStart = 1;
        int matchesEnd = matchesStart + noOfMatches;
        int teamStart = matchesEnd;
        
        int s = 0;
        int t = noOfvertices - 1;
        FlowNetwork G = new FlowNetwork(noOfvertices);
        // add matches edges
        int matchNo = 0;
        for (int i = 0; i < numberOfTeams; i++) {
            if (i == x) {
                matchNo += numberOfTeams - i - 1;
                continue;
            }
            int teamiVertex = teamStart + i;
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (j == x) {
                    matchNo += 1;
                    continue;
                }
                int teamjVertex = teamStart + j;
                int matchVertex = matchesStart + matchNo;
                // match
                G.addEdge(new FlowEdge(s, matchVertex, g[i][j]));
                // match to I
                G.addEdge(new FlowEdge(matchVertex, teamiVertex, Integer.MAX_VALUE));
                // match to J
                G.addEdge(new FlowEdge(matchVertex, teamjVertex, Integer.MAX_VALUE));
                matchNo++;
            }
            // add i -> t
            G.addEdge(new FlowEdge(teamiVertex, t, maxWins - w[i]));
        }
        
        if (noOfMatches != matchNo)
            throw new AssertionError("Invalid No Of Matches. Calculated Value: " + noOfMatches
                    + " - Extracted Value: " + matchNo);
        
        FordFulkerson ff = new FordFulkerson(G, s, t);
        for (FlowEdge e : G.adj(s)) {
            if (e.capacity() - e.flow() > 0) {
                isEliminated[x] = true;
                certificateOfElimination.get(x).addAll(
                    teams.entrySet().stream().filter(et -> et.getValue() != x).filter(
                        et -> ff.inCut(teamStart + et.getValue())).map(et -> et.getKey()).collect(
                            java.util.stream.Collectors.toList()));
                break;
            }
        }
    }
    
    /*private String toString() {
        String newLine = "\n";
        StringBuilder sb = new StringBuilder();
        sb.append(numberOfTeams());
        sb.append(newLine);
        int teamLength = teams.keySet().stream().mapToInt(s -> s.length()).max().orElse(0) + 2;
        for (String team1 : teams()) {
            sb.append(String.format("%-" + teamLength + "s %2s %2s %2s   ", team1, wins(team1), losses(team1),
                remaining(team1)));
            for (String team2 : teams()) {
                sb.append(String.format("%d ", against(team1, team2)));
            }
            sb.append(newLine);
        }
        return sb.toString();
    }*/
    
    private void validateTeam(String team) {
        if (team == null)
            throw new NullPointerException("team");
        if (!teams.containsKey(team))
            throw new IllegalArgumentException("team");
    }
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams5.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
