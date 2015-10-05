package harsu.atmos2k15.atmos.com.atmos2015.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.ControllerConstants;
import app.VolleySingleton;
import helper.EventTableManager;
import helper.ScheduleTableManager;


public class ScheduleUpdateService extends IntentService {

    private ResultReceiver mReceiver;

    public ScheduleUpdateService() {
        super("ScheduleUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mReceiver = intent.getParcelableExtra(AppConfig.RECEIVER);

        }
        sendRequest();
        addData();
    }

    private void sendRequest() {
        final EventTableManager tableManager = new EventTableManager(this);
        final ScheduleTableManager scheduleTableManager=new ScheduleTableManager(this);
        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstants.URL_Events, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Log.e("Schedule.class", s);
                try {
                    Long updatedAt=0l;
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        //todo update updated time
                       if(object.getLong("updated_at")>updatedAt)
                           updatedAt=object.getLong("updated_at");
                        tableManager.updateSchedule(object.getInt("Event_id"), object.getLong("Start_time"), object.getString("venue"));
                        scheduleTableManager.addEntry(object.getInt("Event_id"),object.getInt("tag"),object.getString("Event_Name"),object.getLong("Start_time")*1000,object.getString("venue"));
                    }
//                    SharedPreferences preferences=getApplicationContext().getSharedPreferences(AppConfig.PACKAGE_NAME,MODE_PRIVATE);
//                    SharedPreferences.Editor editor=preferences.edit();
//                    editor.putLong(AppConfig.LastUpdated, updatedAt);
                    deliverResultToReceiver(1, "Refreshed");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                deliverResultToReceiver(0, "Check Internet Connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> temp = new HashMap<>();
                temp.put("tag", "check_time");
//                SharedPreferences prefs=getApplicationContext().getSharedPreferences(AppConfig.PACKAGE_NAME,MODE_PRIVATE);
                temp.put("check_time", "0");
                return temp;
            }
        };
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("1", message);

        if (mReceiver != null)
            mReceiver.send(resultCode, bundle);
    }
    private void addData() {
        EventTableManager tableManager = new EventTableManager(this);
        tableManager.addEntry(63,
                "Economics",
                "Sciences",
                "The Court Room",
                1234669l,
                1234998l,
                "",
                "<h2>Event Description</h2><p>Register in teams of two.</br>A case study will be provided 2 days prior to the competition. <br>Role of Plaintiff: Put up relevant arguments against the concerned firm.<br>Role of Defendant: Defend its maneuver, supported by relevant arguments. <br>The next day, teams choose to be the defendant or the plaintiff, entirely based on first come first basis. (This will be kept hidden from the participants.)On the day of the debate, each team will be paired with another (same side for that particular case). Hence forming teams of four, in a set of 8 for a particular case study. </p> <h2>Format Of Debate</h2> <p>Each contestant speaks for 2 minutes at most (minimum 1 minute), giving the whole set 16 minutes.Followed by a Cross-Questioning session between the two teams lasting 4 minutes.Followed by a Question-Answer session with the audience lasting 2 minutes. </p> <h2>Judging Criteria</h2> <ul> <li>Speaking Skills</li> <li>Argument relevance </li> <li>Spontaneity </li> <li>Body Language </li> <li>Marks to be deducted for a speech less than one minute.</li> <li>The ability of the team members to merge their content(continuity and  &nbsp;&nbsp;&nbsp; consistency of arguments). </li> <li>Marks to be deducted for excessive redundancy. </li> <li>Cross-questioning Ethics . </li> </ul><br> <b>Contact Details: </b><br>Aravind A S : +91 9553325392",
                "",
                "http://bits-atmos.org/Events/img/courtroom.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(65,
                "Economics",
                "Sciences",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<h2>About the Event</h2><p>Participants can participate alone or in teams.<br>Abstracts to be sent in latest by October 3rd, 2015.<br>The final paper can be submitted by october 6th. All the Abstracts are to be sent to eco.bphc@gmail.com </br>Open topics.We shall provide some topics as well. These will be disclosed a little later. <br><br> <b>Contact Details: </b><br>Manaswini Pandey : +91 7729987352 </p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(74,
                "Economics",
                "Sciences",
                "Stock Market Challenge",
                1234669l,
                1234998l,
                "",
                "<h2>About the event:</h2><p>The Economics Association, BITS-PILANI Hyderabad presents an opportunity to create and manage your own portfolio and compete with other players in a risk-free environment by playing the pre-Atmos online event STOCK MARKET CHALLENGE.Stock Market Challenge is easy to use.Simply buy stocks to build your initial portfolio.Continue to track the market and keep an eye on your portfolio to grab opportunities provided etc.Access financial information on stocks to get better insight on its potential and fundamentals.<br><br> <b>Contact Details: </b><br> Manaswini Pandey : +91 7729987352 </p>",
                "",
                "http://bits-atmos.org/Events/img/stockMarketChallenge.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(64,
                "Economics",
                "Sciences",
                "Brotomer",
                1234669l,
                1234998l,
                "",
                "<h2>RANDOMNESS IS THE RISK !</h2><h4>Event Description:</h4> <p>A fun-filled event with exciting prizes to be won and which requires sixth sense, manipulation and instinct to win. Anyone can participate with just a little knowledge of stock market. It's a dynamic event with prices of stocks fluctuating every five minutes. Several rounds of 20 minutes each with many sub-rounds will be held. Every participant will be given a portfolio and some cash in order to trade in the commodities and stocks. Stock price will be decided on the demand and supply and white noise in the market. Crisis, recession, mishaps, elections will be influencing the market in real time.</p> <h4>Features:</h4> <ul> <li>ShortingDividends</li> <li>Dividends</li> <li>Crisis, Mishaps - Real time environment</li> <li>White noise in the market: Sincere effort to simulate all features of real-time market.</li> <li>Commodities tradingCurrency trading</li> <li>Currency trading</li> <li>Real time Graphs</li> </ul> <h4>Judging Criteria:</h4> <p>Participants will be judged on the basis of their final value of portfolio and cash.</p> <h4>Contact Details:</h4> <p>Divyansh Mahajan : +91 8184926405</p>",
                "",
                "http://bits-atmos.org/Events/img/Brotometer.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(66,
                "Economics",
                "Sciences",
                "Policy Dilemma",
                1234669l,
                1234998l,
                "",
                " <p>Writing event.Participants will be put in an economic environment and provided with a problem statement. They need to come up with the best possible solution.Problem Statement is yet to be decided.A qualifier (Quiz) will precede the actual event. <br><br> <b>Contact Details: </b><br>Bhavya Mishra : +91 7729987494 </p>",
                "",
                "http://bits-atmos.org/Events/img/policyDilemma.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(29,
                "Maths",
                "Sciences",
                "Mathefia",
                1234669l,
                1234998l,
                "",
                "<p>Do solving mathematical puzzles give you immense satisfaction? Do problems of discrete mathematics engage the grey cells in your brain? Does your brain search for hidden mathematics in all your problems? Then this event is for you, where </br>questions from various discrete math topics like counting principles, Boolean logic and recurrence relations will make your brain race and give you the chance of being the genius in the room once again.</br>The event will comprise of two rounds. Participation will be allowed in teams of 2 or 3 only. Round 1 will be an hour long written round. The top 10 teams will qualify for the next round. Round 2 will be a sudden-death round. Each team will be given a puzzle to solve in a pre-determined amount of time (1-2 minutes). Teams who manage to crack the puzzle will remain in the competition while teams who fail to solve the puzzle will get eliminated. This will continue till we have a single winning team.Teams of 2 or 3 only. </p> <h2>Contact<h2> <p>Shubham Ranjan:7661079273</br>Anuraj Kanodia:8185003658</p>",
                "",
                "http://bits-atmos.org/Events/img/mathefia.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(27,
                "Maths",
                "Sciences",
                "nCrypton",
                1234669l,
                1234998l,
                "",
                "<p>Date: April 1, 1942</p><p>Block D,<br> Bletchley Park,<br> Buckinghamshire, England.</p> <p>Communications between the Axis Powers has increased significantly over the last couple of weeks. Our listening stations have intercepted a lot of messages being communicated. The Beeston Hill Y Station in Norfolk and Station X in Bedfordshire have confirmed that 5 of those messages originated in Germany. Hut15 is still trying to figure out the destination of those messages. Recent events point to something big coming from the Axis Powers. Prompt actions must be taken to neutralize any such plans. We expect you to decrypt and analyze these messages before it's too late.</p> <p>Commander Alastair Denniston<br>Operational head GC&amp;CS<br></p> <p><b>5 crypts - 90 minutes</b> - all relevant information required to break the crypts will be provided.</p> <h2>Contact<h2> <p>Shubham Ranjan:7661079273</br>Anuraj Kanodia:8185003658</p>",
                "",
                "http://bits-atmos.org/Events/img/math_ncrypton.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(32,
                "Maths",
                "Sciences",
                "Boxed In",
                1234669l,
                1234998l,
                "",
                "<p>An out of the box approach for solving these boxed puzzles is what we are looking for through the event. Contest with others in boxed-puzzle games like Sudoku and Kakuro to prove your worth.</br></br>Teams of 2 or 3 only.</p> <h2>Contact<h2> <p>Shubham Ranjan:7661079273</br>Anuraj Kanodia:8185003658</p",
                "",
                "http://bits-atmos.org/Events/img/Math_boxed-in.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(28,
                "Maths",
                "Sciences",
                "Cubing Atmosphere",
                1234669l,
                1234998l,
                "",
                "<p>You know what? Researchers said that - Thousands of new cells are generated in the adult brain every day, particularly in the hippocampus, a structure involved in learning and memory. Within a couple of weeks, most of those newborn neurons will die, unless the animal(you and me :P) is challenged to learn something new. Learning-especially that involves a great deal of effort-can keep these new neurons alive. One such exercise is cube solving. It is not too easy unless you are really intelligent.So, Do you have that habit of going to the rescue of your neurons? Are you that intelligent one who can solve rubik's cube with ease and willing to prove your talent by competing with best cube solvers in India? Then come join us this October 10th and 11th as a part of our 4th National annual Techno-Management festival- ATMOS '15.</p>",
                "",
                "http://bits-atmos.org/Events/img/RubixCube.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(30,
                "Maths",
                "Sciences",
                "Logica",
                1234669l,
                1234998l,
                "",
                " <p>Voila! AXIOM brings to you another exciting event-\"LOGICA\". You'll be given questions on general reasoning, geometric patterns, probability, etc to tickle your brain cells. No preparations. No prerequisites required. Yay! So, bring along another buddy and join us for sixty fun minutes. You better not miss this one! See you there!</br></br>Teams of 2 or 3 only.</p> <h2>Contact<h2> <p>Shubham Ranjan:7661079273</br>Anuraj Kanodia:8185003658</p> ",
                "",
                "http://bits-atmos.org/Events/img/logicaMAthsa.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(31,
                "Maths",
                "Sciences",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<p>God used beautiful mathematics in creating the world - P.A.M. Dirac</br></br>Do you have a mathematician's brain? Do you believe that everything in the universe can be broken down to simple and sometimes not so simple mathematics?  Then this paper presentation is your platform for you to express yourself. All research/study in mathematics are accepted.</br></br>Interested participants would have to submit their abstracts before 5th October, 2015. Presentations are expected to be at the most of 20 minutes. You can mail your abstract to mathassoc.bphc@gmail.com</br></br>Participants will then be shortlisted based on their abstracts and selected ones will be intimated via email about further details regarding the event.</p> <h2>Rules:<h2> <ul> <li>The maximum number of participants in a group cannot be more than three.</li><li>No restriction is on the composition of the team , i.e., a team can have members from different colleges and also there is no limit on the no. of teams from a college.</li> <li>A participant will be the part of one and only one team and cannot submit more than two abstracts.</li> </ul> <h2>Judging Criteria:<h2> <p>The papers will be judged by an unbiased panel on their originality, objective, implications, conclusions, methodology, analysis, applications and also limitations.</p> <h2>Contact<h2> <p>Shubham Ranjan:7661079273</br>Anuraj Kanodia:8185003658</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(40,
                "Chemistry",
                "Sciences",
                "CHEM HUNT",
                1234669l,
                1234998l,
                "",
                "<p>Molecules all around us have stories of their own. If you care to find out, come join us for a (re)action filled evening.</p><p><b>Rules:</b></p><li>   Teams of 3 to 5 members can participate in this event.</li><li>   The contest will consist of two rounds &#150; the prelims and the main event.</li><li>   The prelim will consist of a formal chemistry quiz of 1 hour (the difficulty level of questions will be of 12th standard.)</li><li>   Use of internet is strictly prohibited during this round.</li><li>   The top 6 teams of the prelims will advance to the main event.</li> <li>   The main event will be a treasure hunt type event in which the contestants are required to find a commercial item regarding the question within an hour.</li> <li>   Use of internet is allowed in this round.</li> <li>   The top 3 teams after the mains will receive prize money.</li> <b>Eligibility Criteria:</b> <li>   This contest is open to all and the only eligibility is that you must be present there.</li> <b>Judging Criteria:</b> <li>   In the prelims, the team with most correct answers will be ranked first and so on.</li><li>   In the mains, the team having maximum number of correct items will be ranked first and so on.</li> <li>   In case two teams get the same number items, the tie will be broken on the basis of total time taken by each team.</li> <li>   In case of any controversy the decision taken by the event co-ordinator will be final.</li> <strong>Registration:</strong> <li>   The registration for teams is free and can be done online or on-spot but it must be done 30 minutes before the event.</li><br>For any details contact:<br>SRIKAR PRASAD,<br> 9059806167. <p></p>",
                "",
                "http://bits-atmos.org/Events/img/Chem-chemhunt.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(39,
                "Chemistry",
                "Sciences",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<p>The country which is in advance of the rest of the world in chemistry will also be foremost in wealth and general prosperity -William Ramsay.</p><p>Love chemistry?  Have innovative ideas?  Well, wait no more. In this Paper Presentation, anything goes as long as it is related to chemistry. Any research work/projects will be entertained.<br><br>The participants are expected to send the abstracts before 2nd October, 2015. Their presentation should be at most, 20 minutes. You can send your abstract to sasrikar2@gmail.com or bphc.chemistry@gmail.com  .(Abstract should not be more than 50 lines.)<br><br>Once their abstract is shortlisted, the participants will be intimated via email giving them the details about the venue and date for presenting the paper. <br><br>The top two participants will receive a cash prize.</p><b>RULES:</b><li>  A team can consist of up to 3 people, not necessarily from the same college.</li><li>  No participant can be a part of more than 1 team.</li><li>  There is no restriction on the number of teams from the same college.</li><li> A participant should not submit more than 2 papers.</li><br><br><strong>JUDGING CRITERIA:</strong><h2>Date of the event:</h2><p>Oct 11-12, 2015</p>The papers will be judged on:<li>   Originality</li><li>   Simplicity </li><li>   Methodology</li><li>   Findings and Analysis</li><li>   Conclusions</li><li>   Implication/Application/Limitations </li><br><br><strong>REGISTRATION:</strong><br><strong>CONTACT:</strong><br>SRIKAR.B (9059806167)<br>AMITESH SONI (772987222)<br>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(41,
                "Chemistry",
                "Sciences",
                "BOOZE Quiz",
                1234669l,
                1234998l,
                "",
                "<p>You might have drunk it, spilled it or went bankrupt for it, but do you actually know your BOOZE?? Prove that you are the best. Come join us for an evening of fun and facts.</p><p><strong>Rules:</strong></p><li>It will be a standard quiz which will be held in two rounds with questions related to mainly alcohol and its derivatives amongst other things.</li><li>Teams of three or less are allowed.</li><li>The top 8 teams will advance to the finals.</li><li>The final top three teams will get prize money</li><br><strong>NOTE:</strong>Use of Internet is strictly prohibited. If caught, you will be disqualified immediately.<br><br> <strong>Eligibility Criteria:</strong>This quiz is open to all and the only eligibility is that you must be present there.<br><br> <strong>Judging Criteria:</strong>Standard quiz rules will be followed and the team with maximum points will be ranked first and so on.In case of any controversy, the decision of the quizmaster will be final.<br><br> <strong>Registration:</strong>Registration can be done online or on-spot by paying a minimal fee of &#8377; 0.00 only<br><br> <strong> Contact:</strong><br>Aniruddha Singhal (9542969134).<br>RISHABH (9542970530).",
                "",
                "http://bits-atmos.org/Events/img/Chem-BoozeQuiz.jpg",
                0,
                0d,
                0);

        tableManager.addEntry(49,
                "Bio and Pharmacy",
                "Sciences",
                "NATURE O TRIVIA",
                1234669l,
                1234998l,
                "",
                "<b>Description:</b><br>Everybody has a corner of their brain which serves as a repository for all the interesting facts and trivia about the nature. Trigger your grey cells and fire your neurons for this life sciences quiz!<br><br><b>Number of members per team- Maximum 3</b><br><br><b>Rounds-</b> First round will consist of questions pertaining to natural science. Top few teams will be selected for the 2nd round.</p><h4>Contact:</h4><br><p>Priyanka: +91-9542556477</br><br>Sanjana: +91-8185920612</p>",
                "",
                "http://bits-atmos.org/Events/img/nature-o-trivia.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(36,
                "Biology",
                "Sciences",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<p>Paper Presentation</p><br><p><b>Description</b>:<br>Bring out the scientist in you! Here at ATMOS 2015 we present you a platform to showcase your stellar research and who knows you might be the next Darwin!<br> <br> <b>Eligibility</b>- Original work related to any field of Biology will be accepted Number of members per team- <b>Maximum 3</b><br><br> <b>Judging Criteria</b>- Abstracts (about 250 words) have to be sent to bphc.bio@gmail.com on or before <b>30th September 2015</b>.<br><br> Selected participants have to give a Powerpoint Presentation on campus during Atmos'15.<br> Further details regarding the Powerpoint presentation will be mailed to the selected participants</p> <h4>Contact:</h4> <p >Priyanka: +91-9542556477</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(38,
                "Bio and Pharmacy",
                "Sciences",
                "Anatomy of Murder",
                1234669l,
                1234998l,
                "",
                "<p></p><br>Amazed by the crime scenes in CSI? Wanna be like Dexter? Come feel the adrenaline rush at ATMOS'15 in BPHC.<br><br> <b>Number of members per team- Maximum 3</b><br><br> <b>1st round:</b> Written quiz testing your basic detective skills.<br> <b>2nd round:</b> Trace footprints, analyze blood, gather evidence and find out who the real killer is!<br> <b>Warning:</b> This event is not for the faint hearted!<br><br><p> <b>Contact:</b><br>Kriti Sharma:8185051379 </p>",
                "",
                "http://bits-atmos.org/Events/img/getAwayMurderChemical.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(51,
                "Pharmacy",
                "Sciences",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<p>Paper Presentation</p><br> <p><b>Description</b>:<br>Bring out the scientist in you! Here at ATMOS 2015 we present you a platform to showcase your stellar research and who knows what you can discover! <br> <br> <b>Eligibility</b>- Original work related to any field of Pharmacy will be accepted Number of members per team- <b>Maximum 3</b><br><br> <b>Judging Criteria</b>- Abstracts (about 250 words) have to be sent to bphc.panacea@gmail.com on or before <b>5th October 2015</b>.<br><br> Selected participants will have to give a seminar on campus during Atmos’15.<br> Further details regarding the seminar will be given to the selected participants through e-mail.</p> <h4>Contact:</h4> <p>Sanjana: +91-8185920612</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(58,
                "Physics",
                "Sciences",
                "Phygure IT",
                1234669l,
                1234998l,
                "",
                "<p>Life around us is a puzzle ,with the understanding of Physics and common sense humans have unraveled deep mysteries of universe.</br></br>We invite you to PHYgure out interesting problems with your reasoning skills and understanding of nature .</br></br>Complete the challenge in 90 minutes.</br></br>Score maximum points to win the event.</br></br>Registration for teams of 2 and of course for free.</br></br>Prerequisite : None.General information about laws will be provided .</br></br>Exercise your grey cells to complete the challenge. </p><h2>Contact:</h2><p>Ridam Jain :8185051470</br><br>Rahul shrinivasan :9848959753</br><br>Email:spectrum.bphc@gmail.com </p>",
                "",
                "http://bits-atmos.org/Events/img/phygureIt.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(59,
                "Physics",
                "Sciences",
                "HONEST PHYSICS",
                1234669l,
                1234998l,
                "",
                "<p >Well all of us watch movies ,and sometimes movies go too far .Bring out the Stephen Hawking  in you to catch all the criminals who break the laws of physics ,because there are some rules that cannot be broken .</br></br>Spot the impossible in the movies,games and videos in 90 minutes</br><br>Score maximum points to win the events </br></br>Registration for teams of 2  and of course for free(we don't charge for fun).</p><h2>Contact:</h2><p>Ridam Jain :8185051470</br><br>Rahul shrinivasan :9848959753</br><br>Email:spectrum.bphc@gmail.com </p>",
                "",
                "http://bits-atmos.org/Events/img/honestPhysics.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(61,
                "Physics",
                "Sciences",
                "Google Sky",
                1234669l,
                1234998l,
                "",
                "<p >It's fascinating how universe is so vast ,and how little we know about it .Solve an astronomical quiz with your smartphone and an app(Google Sky Map) with clues distributed all around the campus .Think of it as cosmic treasure hunt .</p><h2>Rules</h2><ul><li>Using Google Sky map (installed app) and posters at various checkpoints ,try to solve the given quiz.</li><li>At every checkpoint receive clues for every question in the quiz. </li><li>solve as many questions as possible within 120 mins.</li><li>Score maximum points to win the event.</li></ul><h2>Registration</h2><p>On the spot registration for teams of 3 or less  with a nominal charge. </p><h2>Contact:</h2><p >Ridam Jain :8185051470</br><br>Rahul shrinivasan :9848959753</br><br>Email:spectrum.bphc@gmail.com </p>",
                "",
                "http://bits-atmos.org/Events/img/skymaps.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(62,
                "Physics",
                "Sciences",
                "Water Rocket",
                1234669l,
                1234998l,
                "",
                "<p >Construct your own water propelled rocket .Rockets can be multistaged ,multi thrust or innovative in your own way to make a water based projectile.</br></br>Challenge is simple, water should be used as its reaction mass.It's no rocket science.</p><h2>Judging criteria:</h2><ul><li>There will be three rounds </li><li>First round tests the range of the rocket.</li><li>Second round tests the accuracy of the rocket, that is rocket should reach the specified target.Closer to the target higher the points obtained.</li><li>Third round tests the in air time of the rocket ,that is how long the rocket takes to reach back to earth.</li></ul><p>Rocket is allowed to be modified in between the rounds,for optimum result.</br></br>Score maximum points to win the events  </p><h2>Event Details :</h2><ul><li>Water and air pump will be provided. </li><li>As per the weather conditions some judging criteria can be changed.</li><li>Team should not have more than three participants </li><li>Registration starts 30 minutes before the event.</li></ul><h2><br>Contact:</h2><p><br>Ridam Jain :8185051470</br><br>Rahul shrinivasan :9848959753</br><br>Email:spectrum.bphc@gmail.com </p>",
                "",
                "http://bits-atmos.org/Events/img/waterRocket.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(60,
                "Physics",
                "Sciences",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<p>Do want to become immortal.Being a living being we might die someday but we can leave behind the work we do ,we can leave behind the ideas we have .Our ideology become immortal ,great physicist might not be there with us but their work and their theories are still alive.An idea/research in physics is the closest form of immortality you can reach .We invite you to present your idea/research to this world.</br></br>Presentation of the idea is expected to be of at most 20 minutes. You can mail us your abstract to spectrum.bphc@gmail.com latest by 5 October .</br></br>Participants will then be shortlisted based on their abstracts and selected ones will be intimated via e-mail about further details regarding the event.</br></br></p><h2>Rules:</h2><ul><li> The maximum number of participants in a group cannot be more than three. </li><li> A participant will be the part of one and only one team and cannot submit more than two abstracts.</li></ul><h2>Judging Criteria:</h2><p>The papers will be judged by an unbiased panel on their originality, objective, implications, conclusions, methodology, analysis, applications and also limitations.</p><h2>Contact:</h2><p>Ridam Jain :8185051470</br><br>Rahul shrinivasan :9848959753</br><br>Email:spectrum.bphc@gmail.com </p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(10,
                "Computer Science",
                "Technical Events",
                "AlgoManiac",
                1234669l,
                1234998l,
                "",
                "<h2>Describing The Event</h2><p >Do you love following a fixed protocol and rules to problem solving? Maybe you use certain steps to decipher that logic? Algomaniac is just the place for you. The 'certain steps', i.e. algorithms are at the crux of this annual event. But if you've answered the questions asked above in negative, do not worry! The event also aims at testing the logic, common sense and implementation methods of the participants.</p><h2>Algomaniac consists of two rounds:</h2><p>Round 1: - This round consists of riddles and puzzles. Of course, how can you be good at algorithms unless you are good at puzzles .No knowledge of algorithms is required for this round. Anyone can participate.</p><p>Round 2:- Now, as we know that you are good at algorithms, let's get serious, this is the final round. This round consists of 9-10 questions.</p><h2>Event Rules and Guidelines:</h2><p>1. Bring your own Pens.</p><p>2. The number of people getting through to the second round is completely based on the performance of the candidates in the first round. The final decision on the number of candidates qualifying for the second round is solely the decision of the Organizer.</p><br><br><b>Contact:</b><br>Bipin - 7729841666 <br> Ankur - 9700448411<br><br><p><b>Dates:</b> Round1-10th October &nbsp;&nbsp; Round2-11th October</p>",
                "",
                "http://bits-atmos.org/Events/img/CSEalgomaniac.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(9,
                "Computer Science",
                "Technical Events",
                "Firefox OS Midnight Appathon ",
                1234669l,
                1234998l,
                "",
                "<h2>Describing The Event</h2><p >Firefox OS Midnight Appathon is a 2 Day event aimed at providing a platform to open source enthusiasts to come and learn the all new Firefox OS for phones and develop applications for the Firefox Marketplace overnight. All you need to do is to turn up with your laptop and the motivation to keep churning caffeine to code all night. </p><br><p>There will be an introductory session on day 1 which will quickly run fresher's through the basics and also serve as a refresher for other experienced participants. </p><br><p>he event agenda is as follows</p><p><b>Day 1(10th October)</b><br>>   9:30 AM:  -          Commencement of Event <br>>   9:30-1:00PM: -   Introductory Session-1.<br>>   1:00-2:00PM:  -  Lunch Break.<br>>   2:00-5:00:    -      Introductory Session-2.<br>>   5:00-6:00PM: -   Refreshment Breaks.<br>>   6:00-7:00PM: -   Briefing Session for Hackathon.<br>>   7:00-10:00PM: -  Commencement of Hackathon.<br>>   10:00-12:00PM:  - Possible Skype call  in between<br><br><b>Day 2(11th October)</b><br>>   12:00-6:00PM:  -     Continuation of Hackathon  <br>>   6:00-7:00PM:    - Evaluation of Apps<br>>   7:00-7:30PM:    - Swag Distribution.<br>>   7:30-8:00PM:    - Wrapping up the event.<br></p><h2>Event Rules and Guidelines:</h2><p>1)   The participants can form teams of 3 or lesser for the duration of the hackathon.<br> 2)  The decision of the Judges will be binding on the participants.<br>3)  Participants are required to register by <a href=https://docs.google.com/forms/d/13jnCYWGHRD_1ge2wE24pDVKzFkGvNE1SxtNPakS-EGY/viewform>clicking here</a> to participate in the event.<br><br><b>Contact:</b><br> Naveen Jafer:+91-9603445607</p>",
                "",
                "http://bits-atmos.org/Events/img/mozillaAppathon.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(8,
                "Computer Science",
                "Technical Events",
                "Microsoft Hackathon",
                1234669l,
                1234998l,
                "",
                "<h2>Describing The Event</h2><p >Microsoft hackathon is a two-day 24 Hour Hackathon on DIGITAL INDIA aimed at providing a platform to developers and designers to transform their ideas into outstanding apps under the mentorship of Microsoft experts.  All you need to do is turn up with a laptop and knowledge to build things, and we will handle the rest.</p><br><p>The participants can build the application on any platform as long as they use the following platforms<br>1)  Windows 10 UWA: Build apps for Windows 10 devices - Desktop, Phones, IoT.<br>Use Microsoft Kinect, Microsoft Band (TBD), Raspberry Pi/Intel Galileo<br>2)  Windows Azure Services: Azure ML and HD Insight.<br>3)  Bing: Search, Speech, Translation, Maps, Cortana.<br>4)  Office Add-Ins (Apps for Office): O365 Add-ins and Apps for Word, PowerPoint, Excel, SharePoint, Project, Outlook.<br>5)  Android and iOS Apps: Should consume Microsoft Services. Built using   Visual Studio+Xamarin.<br><br>There will be an introductory mentoring session on Day 1, followed by 24 hours(6:00PM(9th)- 6:00PM(10th)) of intensive coding and development. The participating teams will be awarded certificates of participation in the hackathon, on submission of completed apps. The apps will be judged by a panel of experts and the top three winners will be awarded cool prizes at the end of the presentation. The mentors will continue providing remote mentoring via e-mail and/or Skype for a period of 5 weeks after the hackathon concludes.<br>Students will also receive Free Dreamspark Keys and Free Azure Subscription Keys.<br> </p><h2>Event Rules and Guidelines:</h2><p>1.   Maximum of 4 members per team.<br>2.  The teams should use Visual Studio tools, Azure and/or other Microsoft services.<br>3.  The decision of the Judges will be final and binding on all participants.<br></p><h2>REGISTRATION</h2><p>Prior Registration for the event is Compulsory. You will be shortlisted based on the Idea and MVA courses that have been completed. Mails intimating your confirmation will be sent a couple of days after you register. Register at the following Link <a href=http://goo.gl/forms/phbq4l2jBn> REGISTER HERE. </a><br><br>Participants are strongly encouraged to complete the following MVA Courses online before they appear for the Hackathon. <br><br>1)  C# Fundamentals for Absolute Beginners<a http://www.microsoftvirtualacademy.com/training-courses/c-fundamentals-for-absolute-beginners>Click Here</a><br>2)  a) Learn about JavaScript <a href=http://www.microsoftvirtualacademy.com/training-courses/javascript-fundamentals-for-absolute-beginners>Click Here</a><br>b) Learn about HTML5 & CSS3 <a href = http://www.microsoftvirtualacademy.com/training-courses/html5-css3-fundamentals-development-for-absolute-beginners>Click Here</a><br>3)  a) Developing Universal Windows Apps with C# and XAML (1st 3 modules <a href=http://www.microsoftvirtualacademy.com/training-courses/developing-universal-windows-apps-with-c-and-xaml>Click Here</a>)<br>b) Learn how to create UI with Blend <a href=http://www.microsoftvirtualacademy.com/training-courses/designing-your-xaml-ui-with-blend-jump-star>Click Here</a><br>c) Create a sample Universal App i) <a href=http://www.microsoftvirtualacademy.com/training-courses/quick-start-challenge-universal-app >Click Here</a><br>ii)Or <a href=http://www.microsoftvirtualacademy.com/training-courses/developing-universal-windows-apps-with-html-and-javascript-jump-start>Click Here</a><br>4)  a) Microsoft Azure Fundamentals <a href=http://www.microsoftvirtualacademy.com/training-courses/microsoft-azure-fundamentals-websites>Click Here</a><br>b) Storage & Data <a href=http://www.microsoftvirtualacademy.com/training-courses/microsoft-azure-fundamentals-storage-and-data>Click Here</a><br>5)  a) Azure Mobile Services & API Management <a href=http://www.microsoftvirtualacademy.com/en-US/training-courses/azure-mobile-services-and-api-management-8904>Click Here</a><br>6)  a) SQL Database Fundamentals <a href=https://www.microsoftvirtualacademy.com/en-US/training-courses/database-fundamentals-8243>Click Here</a><br>b) Windows Azure SQL Database (Chapters 1, 2, & 4) <a href=https://www.microsoftvirtualacademy.com/en-US/training-courses/windows-azure-sql-database-8280>Click Here</a><br>7)  Windows 8.1 UX Design Jump Start<a href=https://www.microsoftvirtualacademy.com/en-us/training-courses/windows-81-ux-design-jump-start-8235>Click Here</a><br><br>Some good resources to get started on Ideation of your product.<br>1)  <a href=http://aka.ms/AppsForAsia>AppsForAsia</a><br>2) <a href=http://data.gov.in>Data Gov</a><br> </p><h2>Judging Criteria</h2><p>The application will be judged based on the idea, the implementation of the app and its utility.<br>1)  Execution: 50% <br>2)  Innovation: 30% <br>3)Business Viability: 20% </p><p><b>Contact:</b><br> Naveen Jafer:+91-9603445607</p>",
                "",
                "http://bits-atmos.org/Events/img/microsoftHAckathon.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(11,
                "Computer Science",
                "Technical Events",
                "Googled",
                1234669l,
                1234998l,
                "",
                "<h2>Describing the Event</h2><p >Information Systems Association in association with Quiz Club of Hyderabad presents Googled, an event where none but only Google can be your savior. So, if you know how to tame the beast, you are in for a roll!</p><h2>Googled consists of 2 rounds:</h2><p><b>First round:</b> Participating teams have to find answers to a set of simple yet tricky, objective type questions using their googling skills.</p><p><b>Second round:</b> The challenge becomes tougher, trickier and all the more nerve wrecking. A sort of on-stage round wherein the selected teams from the previous round compete against each other to score a maximum, in order to ace the event.</p><h2>Event Rules</h2><p>1. On-spot registrations. Register in teams of two.</p><p>2. Top 8 teams from the first round battle it out in the final round.</p><p>Rules for each round are intended to hold an element of surprise. Hence, will be revealed at the venue itself.</p><p>4. Get your own pens.</p><p>5. No electronic gadgets of any type allowed in the venue.</p><p>6. In any case of discrepancy, the decision of the organizers will be final and binding.</p><h2>Judging Criteria</h2><p>In general, weightage will be given to the response time and correctness of the answer. Rules specific to each round will be unveiled at the venue.</p><br><br><b>Contact:</b><br>Praneeth - 9441193906 <br>Shrey - 9700445681<br><br><p><b>Dates:</b> Round1-10th October &nbsp;&nbsp;Round2-11th October</p>",
                "",
                "http://bits-atmos.org/Events/img/CSEgoogled.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(54,
                "Computer Science",
                "Technical Events",
                "Interstellar",
                1234669l,
                1234998l,
                "",
                "<p>Interstellar is our themed event for ATMOS this year. Write the intelligence software code of a robot travelling in space.</br>We will provide you an API for basic actions of the robot - moving, firing, deriving status etc. and a GUI simulator to test your robot</br>Your robot should be able to complete the mission within the simulator.</br>APIs for robot control will be provided in C/C++/Python/Java. The simulator software will be installed on provided machines.</br>There will be a single round. Winners will be selected on basis of progress through simulator.</p><h2>Points are awarded for:</h2><ul><li>Highly aggressive and passive policies - Number of enemies shot. High points for both extremes. (10%)</li><li>Fuel economisation - Average volume of fuel in tank (10%)</li><li>Telemetry collection and format (20%)</li><li>Mission distance covered. (40%)</li><li>Mission completion.(20%)</li></ul><h4>Contact:</h4><p>Naveen: +91-9603445607</p>",
                "",
                "http://bits-atmos.org/Events/img/interstellar.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(53,
                "Computer Science",
                "Technical Events",
                "Reverse Coding",
                1234669l,
                1234998l,
                "",
                "<p >Coding can be termed as the art of doing something useful from a set of inputs to a set of outputs.</br>However, this assumes you know the useful stuff you're doing.</br>Reverse coding changes the game. All we give you are the input and output sets. Guess the program and code it.</br>The code must work for hidden input and output sets as well.</br>The event will comprise of two rounds - prelims and finals.</br>Prelims will be on paper - input/output questions in C/C++/Java/Python, guess the program and mental aptitude.</br>Finals will be on an online coding platform, it will feature guessing the program questions.</p><h4>Contact:</h4><p >Naveen: +91-9603445607</p>",
                "",
                "http://bits-atmos.org/Events/img/reverseCoding.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(52,
                "Computer Science",
                "Technical Events",
                "Obfuscated Coding Contest",
                1234669l,
                1234998l,
                "",
                "<p >Academia and industry thoroughly emphasize on writing understandable and maintainable code. We're giving you a break from all that.</br>The Obfuscated Coding Contest lets you go crazy with your code, strive to make it as unreadable as possible. Of course, the code must solve the problems we assign to you. Programming language tricks and hacks, ASCII art and other beautification techniques will earn you brownie points.</br>The contest has two rounds - prelims and finals.</br>The preliminary round will feature 4 questions, 2 of which need to solved within a time frame of 2 hours.</br>Top 10 people move into the final round.</br>A difficult problem (algorithmic) shall be given to be coded in an obfuscated manner. Finalists will have a day to code their solution and present the same to a panel of judges on the next day.</br>Any programming language is allowed.</br>Points are earned for:</br></p><ul ><li>Language specific hacks and tricks (think abstract features of C)</li><li>Code beautification (ASCII art, patterns)</li><li>Unreadable code - difficult to trace program execution (think complicated variable names, inconsistent coding techniques)</li><li>Creativity (overall coding approach)</li></ul><h4>Contact:</h4><p >Naveen: +91-9603445607</p>",
                "",
                "http://bits-atmos.org/Events/img/obfuscatedCode.png",
                0,
                0d,
                0);
        tableManager.addEntry(13,
                "Computer Science",
                "Technical Events",
                "Detective Data Miner",
                1234669l,
                1234998l,
                "",
                "<h2>Describing the event:</h2><p>Do you like solving murder mysteries? Do the stories of Sherlock Holmes and Hercule Poirot excite you? If so, Detective Dataminer is just for you. Step into the shoes of a data analyst who finds himself thick in the plot of a murder. With only his scripting and analytical skills, help him trace the murderer.</p><h2>Problem Statement:</h2><p>20-30 GB of data of archived data, news clippings, tweets and FB posts are available. You may use C/C++/Java/R/Python to process this data and derive useful relationships and information. A case file with a logical solving of the case is available. Fill in the necessary details from the derived information to solve the case and determine the killer.</p><h2>Rules:</h2><p>Teams of two Only C/C++/Java/Python/R are allowed. Two rounds - prelims will have objective aptitude questions (coding and logic puzzles), finals features the case to be solved.</p><br><br><b>Contact:</b><br>Kush- 9542970249<br><br><p><b>Dates:</b> 11th October</p>",
                "",
                "http://bits-atmos.org/Events/img/data-mining01.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(12,
                "Computer Science",
                "Technical Events",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<h2>Describing the event:</h2> <p >The classic Paper presentation is one event that is not to be missed. Paper Presentation at ATMOS provides people from all over India to present us with their ideas, research and their thoughts related to new, emerging technologies or the significance of older ones in the current context. Specifically, the event comprises of two rounds. In the first round, participants are invited to send us the abstract as well as the paper they want to present through email. These entries would go through extensive cross-checking for plagiarism, creativity and choice of topic. Participants selected in this round would be eligible for the second round which is the on-site round to be conducted during ATMOS 2015 at BITS Pilani Hyderabad Campus. The participants would have to provide a presentation in front of a panel, comprising of distinguished faculty, concerning the details of their paper.</p><h2>Event Rules:</h2><p>1. Participants should send the abstracts of their papers to voxcsabphc@gmail.com</p><p>2. Plagiarism up to 30% is tolerated.</p><p>3. Participants selected for the second round must create a formal presentation concerning their paper. Any other form would not be accepted.</p><p>4. Deadline for sending abstracts of your papers is 5th October, 11:55PM</p><br><h2>Judging Criteria:</h2><p>The first round be judged on the basis of the idea or field chosen by the participants. Fields having real life implementations would be preferred. The abstract sent by the participant would be checked for correctness as well as plagiarism. PLEASE AVOID PLAGIARISM. The second round would be based on the presentation of the participant with the judging procedure entirely selected by the panel as such.</p><br><br><b>Contact:</b><br>Sruthi Sagi - 7729987373<br> Manoj G.-7661061747<br><br><p><b>Deadline for sending abstracts of your papers is 5th October, 11:55PM</b></p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion2 (1).jpg",
                0,
                0d,
                0);
        tableManager.addEntry(55,
                "Computer Science",
                "Technical Events",
                "CODE-JAM",
                1234669l,
                1234998l,
                "",
                "<h2>Describing the event:</h2><p >Code-Jam is a premier coding contest of BITS Pilani Hyderabad Campus conducted every year at ATMOS. Free to code in any language from a long list of programming languages, the team of 2 people are majorly judged in mathematics and their understanding and implementation of algorithms. The two levels of the contest aim to determine the best coder present at ATMOS. </p><h2>Rounds:</h2><p><b>Prelims</b></p><p>On-Site Round: Teams from the above round will be invited for the first On-Site Code-Jam Round to be conducted during ATMOS'15 and will compete with teams selected from our own campus</p><br><p><b>Final Round</b></p><p>Top 20 teams will be selected from the above round to participate in the final and second on-site round, where the best and meticulous programmers battle it out to become the Tesla of programming throughout the nation. The top 20 teams are awarded certificates and the winners (3 teams) receive hefty cash prizes and the immense reverence associated with it.</p><br><br><b>Contact:</b><br>Sagar - 7729987432<br> Kushagra - 7729887330<br><br>",
                "",
                "http://bits-atmos.org/Events/img/codejam.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(57,
                "Computer Science",
                "Technical Events",
                "TopCoder",
                1234669l,
                1234998l,
                "",
                "<h2>Overview of the Event:</h2><p >topcoder is organizing an All India Coding Competition , whose finals will be held during ATMOS '15 at BITS Pilani Hyderabad Campus.The preliminaries for the same will be held online on the 1st of October, 7 pm online.<br>The registration link is given below. <br>The top 100 participants from the online round will be invited to BITS Pilani Hyderabad Campus for the onsite final round on 10th of October . <br>In addition to this , for those new to the art of competitive coding , topcoder will also be giving a talk on the same. <br></p><h2>Registration for the First Round </h2><a href=https://www.topcoder.com/register/?utm_source=hmehta&utm_campaign=BitsHyderabad&utm_medium=Appirio >Registration Link</a><br><br><b>Contact:</b><br>Sagar - 7729987432<br> Kushagra - 7729887330<br><br>",
                "",
                "http://bits-atmos.org/Events/img/topcoder.png",
                0,
                0d,
                0);


        tableManager.addEntry(68,
                "Civil",
                "Technical Events",
                "Smart City Innovation Challenge",
                1234669l,
                1234998l,
                "",
                "<h2>About Smart Cities</h2><p > Smart city has emerged as a buzzword in India ever since Prime Minister Narendra Modi outlined his vision for creating a hundred Smart cities. And, aside from the hype around the term Smart cities it does raise some pertinent questions such as what does it really mean to be a smart city? and why should Indians care about the smartness of their city? Indian cities are unique in that they confront the challenges of not only the 20th and 21st centuries but also some remnants from the 19th century. While cities like Bengaluru are participants in global innovation and increasingly compete with Silicon Valley to further enhance innovation, they also need to tackle important issues such as providing basic sanitation, public transport, clean water and effective waste management. So, the traditional approach of managing and maintaining our cities must change - both in mindset and in the way we administer them.</p> <h2>Call for City Innovations</h2> <p> There is tremendous potential in India to build an effective ecosystem to enable our burgeoning urban areas to become smart by using digital technology. This in turn will create employment opportunities and contribute to economic growth through innovation. Our cities are fast becoming the defining units of human habitation. How smartly we build, manage and operate our cities will be the single biggest determinant of our people's future. We owe it to our future generations to make our cities smart through the use of technology. <br>We request all the Public and Private bodies to join hands and show their mettle by coming up with smart, sustainable and long-term solutions. Applicants will be invited to present and showcase their technology during the National Techno-Management Fest of BITS Pilani Hyderabad Campus, ATMOS 2015(October 8-12, 2015). <b>There is no fee to submit your innovation.</b> </p> <h2>Innovation Areas </h2> <p></p><ul><li> Power Generation</li><li>Grid &amp; Power Management </li><li>Cyber &amp; Grid Security </li><li>Energy Storage </li><li>Transportation Solutions </li><li>Sensors &amp; Communications </li><li>Environmental Solutions</li><li> Water Management </li><li>Waste Management </li><li> Buildings &amp; Efficiency </li><li>Facility Management </li><li> Metering &amp; Metrics </li><li>Data Analysis/Management </li><li> Services/Solution Providers </li></ul> <p></p> <h2>Contact Details:</h2> <p> Hryidesh Tewani: +91-9000755319</p>",
                "",
                "http://bits-atmos.org/Events/img/Civil_smartCity%20(1).jpg",
                0,
                0d,
                0);
        tableManager.addEntry(25,
                "Civil",
                "Technical Events",
                "JAM",
                1234669l,
                1234998l,
                "",
                "<p>Traffic congestion brings equilibrium to the world, whether you are rich or poor, everybody lives on the road<br>Thousands have lived without love, not one without water. <br> And God said, 'Let there be light' and there was light, but the Electricity Board said He would have to wait until Thursday to be connected.<br> How many of us have cursed the place we live?  Due to problems of transport every day we struggle to get ahead. It's time to take up the challenge and come up with ideas to eradicate these problems. This event throws a real life challenge to the aspiring civil engineer to bring forth all their creativity and zest for design. This is your chance to put your brains to the service of highways and metros of tomorrow. <br> <br> Rules:</p><ul> <li>A participant team may comprise of 3 or 4 people (not more than 4). </li><li>The problem statement would be provided on spot during the event, for which the most feasible solution would be awarded. </li><li>No pre-registration required. </li><li> The judges include eminent professors from Department of Civil Engineering, BITS Pilani Hyderabad Campus. </li><li>The results will be announced during the festival.</li></ul> <p></p> <h2>Contact Details:</h2> <p>Satya: +91-7730835423 </p>",
                "",
                "http://bits-atmos.org/Events/img/CivilJam.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(26,
                "Civil",
                "Technical Events",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<h2>Submission Guidelines</h2> <p></p><ul><li>Team size is restricted to maximum of two </li><li>U.G. Students of Civil Engineering discipline can participate for any topic related to their branch. </li><li>Maximum pages are restricted to five. </li><li>Presentation time is 8 Min (Presentation) + 2 Min (Q&amp;A). </li><li>The students must carry their institute or any other Identity card in person &amp; produce on demandby organizing committee/coordinators. </li><li>The participants will bear their own Travelling expenses. </li><li>Final decisions will be of Judges/Organizers only. </li><li>Name of Branch, email id, contact no. should be mandatory. </li><li>Participants should send the abstracts of their papers to cea.bitshyd@gmail.com by October 3rd, 2015.  </li></ul><p></p> <h3>Scoring</h3>The paper and the presentation have a maximum combined score of 125 points. A maximum of 80 points may be awarded for the paper, and a maximum of 45 points may be awarded for the presentation.<br> <br>1. Paper Scoring: Judges will evaluate and score the papers using the maximum point values shown below: <br>Abstract: 5 marks <br> Organization (Introduction, Body, Conclusion): 8 marks <br> Background information/ literature review: 7 marks <br> Technical content and accuracy of the information: 20  marks <br> References: 5 marks <br>Use of figures and tables: 5 marks <br> Formatting: 5 marks <br> Grammar: 8 marks <br> Written Expression and style: 7 marks <br> Overall clarity and quality of paper: 10 marks <br> Total: 80 marks <br>2. Presentation Scoring: Judges will evaluate and score the presentation using the maximum point values shown below: <br> Organization of the material: 5 marks <br> Technical command of the subject matter: 10 marks <br> Presentation Style: 7 marks <br> Correct use of technical language and grammar: 10 marks <br> Time (8 minutes maximum): 5 marks <br>Overall clarity and quality of the presentation: 8 marks <br>Total: 45 marks <p> <br><br> <b>Contact Details:</b><br> </br>Satya: +91-7730835423</br><br>Hryidesh Tewani: +91-9000755319</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(43,
                "Chemical",
                "Technical Events",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                " <p style=\"font-size:20px;\"></p><ul style=\"list-style-type:disc\"><li>A formal stage to research and present yourself on any field related to chemical engineering. The most original and creative papers get a platform to showcase the vast potential they have in their respective field.</li>\n" +
                        "                                        <li>A team of maximum three members can be formed. Try to be as original as possible.</li>\n" +
                        "                                        <li>Participants will be judged by faculty of chemical department. A great opportunity to interact with students from different colleges. Winners get cash prize and lots of other exciting goodies.</li></ul><p></p>\n" +
                        "                                        <h2>RULES AND REGULATIONS</h2>\n" +
                        "                                        <ul style = \"font-size:20px;\">\n" +
                        "                                            <li>A team of maximum three members can be formed. Try to be as original as possible.</li>\n" +
                        "                                            <li>Participants have to send their abstract to the fallowing mail ID-bphc.ace@gmail.com</li>\n" +
                        "                                            <li>There is no registration fee applicable. </li>\n" +
                        "                                            <li>Last date for submission of abstract is 03/09/2015.</li>\n" +
                        "                                            <li>Last date for confirmation of abstract is 06/09/2015. </li>\n" +
                        "                                        </ul>\n" +
                        "                                        <h2>Contact Details</h2>\n" +
                        "                                        <p style = \"font-size:20px\">Santhosh Raju V V ' 9966953366</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(15,
                "Electrical",
                "Technical Events",
                "Robowars",
                1234669l,
                1234998l,
                "",
                "<h2>War does not determine who is right;ONLY WHO IS LEFT-Bertrand</h2><h2>Russell</h2><p >The headliner, the crowd-puller, the pure visual euphoria in any ATMOS always had just one name - RoboWars! See the rebirth of the majestic medieval knights in their 21st century avatar. See the robots battle it all out in the mighty space at BITS Pilani Hyderabad Campus. Behold a futuristic projection of Gladiatorial days of combat.</br>The war is about to begin. Tighten the screws, charge your batteries, oil the parts, it's going to get messy. Are you ready?</p><p><b>Problem Statement</b><br>The challenge is to create a robot capable of combating with the opponent.<br><br><b>General Instructions</b><br>- All participants are to build and operate robots at their own risk. Combat robotics is dangerous. Please take care to not hurt yourself or others when building, testing and competing.<br>- Compliance with all event rules is mandatory. It is expected that competitors will comply with the rules and procedures of their own accord and not require constant 33policing.<br>\n" +
                        "\n" +
                        "                                        - Robots will be inspected for safety and reliability before being allowed to compete. \n" +
                        "\n" +
                        "                                        As a builder you are obligated to disclose all operating principles and potential \n" +
                        "\n" +
                        "                                        dangers to the Event Coordinators.<br>\n" +
                        "\n" +
                        "                                        - Proper activation and deactivation of robots is critical. Robots must only be activated \n" +
                        "\n" +
                        "                                        in the arena, or with expressed consent of the Coordinators.<br>\n" +
                        "\n" +
                        "                                        - Moving weapons that can cause damage or injury must have a clearly visible locking \n" +
                        "\n" +
                        "                                        device in place at all times when not in the arena. Locking devices must be clearly \n" +
                        "\n" +
                        "                                        capable of stopping, arresting or otherwise preventing harmful motion of the weapon.<br>\n" +
                        "\n" +
                        "                                        - It is expected that all participants must follow basic safety practices when working on the robot at the event. <br>\n" +
                        "\n" +
                        "                                        - Any kind of damage to the arena or people around it would mean immediate disqualification without the need for proper explanation.<br>\n" +
                        "\n" +
                        "                                        - In all cases the decision of the Coordinators at the venue would be final, binding and unquestionable.<br><br>\n" +
                        "\n" +
                        "                                        <b><br>\n" +
                        "\n" +
                        "                                        <b>BOT Specifications:</b><br>\n" +
                        "\n" +
                        "                                        <b>1. Style</b><br>\n" +
                        "\n" +
                        "                                        1.1. Robots can be built using wheels, tracks and legs (Walker robots).Jumping, flying bots are strictly prohibited.\n" +
                        "                                        <br>\n" +
                        "\n" +
                        "                                        1.2. Other styles or methods may be considered, but be sure to contact the Coordinators before registering.<br>\n" +
                        "\n" +
                        "                                        1.3. 'Cluster Bots' - robots consisting of two or more components are allowed, but they'must enter the arena as a single object. If 50% or more of the Cluster Bot is immobilized, the robot will be deemed to have lost that particular battle.<br>\n" +
                        "\n" +
                        "                                        1.4. Bot can be controlled either through wired or wireless controller.</br> \n" +
                        "\n" +
                        "\n" +
                        "                                        <b>2. Weight</b><br>\n" +
                        "\n" +
                        "                                        2.1. The maximum weight allowed for the robot is 45kgs for wired bots (This assumes that the power supply for the robot is off-board). For wireless bots the maximum weight allowed is 60kg.<br>\n" +
                        "\n" +
                        "                                        2.2. If interchangeable weapons / armour systems are used, the weight measured is the one with the heaviest set-up in place.<br>\n" +
                        "\n" +
                        "                                        2.3. A 50% relaxation in weight and dimensions would be given to absolute walker bots. Decision of whether a particular Bot is an absolute walker Bot would be taken by the organisers, upon abstract submission.<br>\n" +
                        "\n" +
                        "                                        <b>3. Dimensions</b><br>\n" +
                        "\n" +
                        "                                        3.1. Overall dimensions must not exceed 60cm x 60cm x 60cm (W x B x H) respectively.<br>\n" +
                        "\n" +
                        "                                        3.2. Width, breadth and height are measured to the extremities of the robot, i.e. including any overhanging bodywork, weaponry or protrusions. The measurements would be taken in the position that the robot would enter the arena, i.e. if the robot starts a fight with weaponry or other devices in a retracted position, the width, breadth and height are measured with these in this retracted position.<br>\n" +
                        "\n" +
                        "                                        3.3. After every duel the robot is expected to autonomously retract itself in its original dimensions provided that these have been altered during the fight. Failure to do this will result in the robot losing the battle.<br>\n" +
                        "\n" +
                        "                                        <b>4.  Power</b><br>\n" +
                        "\n" +
                        "                                        4.1. The machine can be powered electrically only. Use of an IC engine in any form is not allowed. The only permitted batteries are ones that cannot spill or spray any of their contents when damaged or inverted. This means that standard automotive and motorcycle wet cell batteries are prohibited. Examples of batteries that are permitted: (Such as gel cells, lithium, NiCad, NiMH, or dry cells).  <br>\n" +
                        "\n" +
                        "                                        4.2. All on-board voltages above 36 Volts require prior approval from this event. (It is understood that a charged battery's initial voltage is above their nominal value)<br>\n" +
                        "\n" +
                        "                                        4.3. All electrical power to weapons and drive systems (systems that could cause potential human bodily injury) must have a manual disconnect that can be activated within 15 seconds without endangering the person turning it off. (E.g. No body parts in the way of weapons or pinch points.) Shut down must include a manually operated mechanical method of disconnecting the main battery power, such as a suitable high current switch or removable link. Relays may be used to control power, but there must also be a mechanical disconnect.<br>\n" +
                        "\n" +
                        "                                        4.4. All efforts must be taken to protect the battery terminals from a direct short and causing a fire. Failure in doing so will lead to disqualification.\n" +
                        "                                        </br>\n" +
                        "\n" +
                        "\n" +
                        "                                        <b>5. Pneumatics</b><br>\n" +
                        "\n" +
                        "                                        5.1. Robot can use pressurized non-inflammable gases to actuate pneumatic devices. Maximum allowed outlet nozzle pressure is 10 bar. The storage tank and pressure regulators used by teams need to be certified and teams using pneumatics are required to produce the Safety and Security letters at the Registration Desk at the venue. Failing might lead to direct disqualification.</br>\n" +
                        "\n" +
                        "                                        5.2.Participants must be able to indicate the used pressure with integrated or temporarily fitted pressure gauge. Also there should be provision to check the cylinder pressure on the bot. The organisers will not provide any equipment to gauge the pressure.</br>\n" +
                        "\n" +
                        "                                        5.3.The maximum pressure in cylinder should not exceed the rated pressure at any point of time.</br>\n" +
                        "\n" +
                        "                                        5.4.You must have a safe way of refilling the system and determining the on board pressure. </br>\n" +
                        "\n" +
                        "                                        5.5. All pneumatic components on board a robot must be securely mounted. Care must be taken while mounting the pressure vessel and armour to ensure that if ruptured it will not escape the robot.</br>\n" +
                        "\n" +
                        "\n" +
                        "                                        5.6. Entire pneumatic setup should be on-board, no external input (from outside the arena) can be given to the robot for functioning of its pneumatic system. </br>\n" +
                        "                                        <br>\n" +
                        "                                         \n" +
                        "\n" +
                        "                                        <b>6. Hydraulics</b><br>\n" +
                        "\n" +
                        "                                        6.1. Hydraulic pressure is limited to 3000 psi. The competitor must be able to demonstrate the pressure used and carry with them a portable pressure gauge that can be fitted to the system if required to do so by the organizing team. The organisers will not provide any equipment to gauge the pressure.<br>\n" +
                        "\n" +
                        "                                        6.2. The use of accumulators on the Hydraulic circuits is strictly prohibited.<br>\n" +
                        "\n" +
                        "                                        6.3. Hydraulic fluid storage tanks must be of a suitable material.<br>\n" +
                        "\n" +
                        "                                        6.4. The lines must be routed to minimize the chances of being cut.<br>\n" +
                        "\n" +
                        "                                        <b>7. Weapon Systems</b><br>\n" +
                        "\n" +
                        "                                        7.1. All pyrotechnics(refer 7.2); explosives; flames; firearms; corrosives; liquids; electronic devices - e.g. radio jamming, heat-guns, Tesla coils - are banned.<br>\n" +
                        "\n" +
                        "                                        7.2. Small, non-offensive pyrotechnics - e.g. flash puffs - May be allowed at the organiser's discretion.<br>\n" +
                        "\n" +
                        "                                        7.3. Devices using inflammable or combustion-supporting gases are banned.<br>\n" +
                        "\n" +
                        "                                        7.4. i) Untethered projectiles are not allowed.<br>\n" +
                        "\n" +
                        "                                              ii) Tethered projectiles are allowed, but the tether may not exceed 1.5m (approx. 5 ft) in length, \n" +
                        "\n" +
                        "                                        (measured from the center of the robot to the tip of the projectile).<br>\n" +
                        "\n" +
                        "                                        7.5. The speed of any rotating weapons - e.g. circular saws, carbon or steel cutting discs - must not \n" +
                        "\n" +
                        "                                        exceed the manufacturer's specification. The manufacturer's specification must be available for \n" +
                        "\n" +
                        "                                        inspection.<br>\n" +
                        "\n" +
                        "                                        7.6. Rotating hardened steel blades that may shatter are not allowed.<br>\n" +
                        "\n" +
                        "                                        7.7. Commercial blades - e.g. bayonets - must not exceed 20cm/8inches in length. <br>\n" +
                        "\n" +
                        "                                        7.8. All sharp edges of weapons, including fixed weapons - e.g. spikes and robot bodywork in \n" +
                        "\n" +
                        "                                        general that is sharp, MUST be fitted with adequate protection that must be in place at all times \n" +
                        "\n" +
                        "                                        except in the arena. (These guards are not included with the overall weight of the robot).<br>\n" +
                        "\n" +
                        "                                        7.9. Any moving or swinging arms - whether or not they hold sharp and/or rotating weapons - MUST \n" +
                        "\n" +
                        "                                        be fitted with a visible locking pin that shows the arm(s) is securely locked into place.<br>\n" +
                        "\n" +
                        "                                        7.10. NO Lasers are allowed.<br>\n" +
                        "\n" +
                        "                                        <b>8. Control Requirements</b><br>\n" +
                        "\n" +
                        "                                        8.1.  The machine can be controlled through wired or wireless remotes. <br> \n" +
                        "\n" +
                        "                                        8.2. There should be binding capability between transmitters and receivers. The remotes with such facility will only be allowed. <br>\n" +
                        "\n" +
                        "                                        8.3.  The team must have at least four frequency wireless remote control circuit or two dual control circuits which may be interchanged before the start of the race to avoid frequency interference with other teams. The case of any interference in the wireless systems will not be considered for rematch or results. <br>\n" +
                        "\n" +
                        "                                        8.4. Remote control systems from toys might be used. Remote control systems available in the market may also be used.<br>\n" +
                        "\n" +
                        "                                        8.5. Nonstandard or self-made remote control systems must first be approved by the organizers. <br>\n" +
                        "\n" +
                        "                                        8.6. Team should pair up the wireless remote with the machine before putting it into the \n" +
                        "\n" +
                        "                                        arena. <br>\n" +
                        "\n" +
                        "                                        8.7. In case of wired control, the teams are expected to bring enough wire according to the arena \n" +
                        "\n" +
                        "                                        specifications which would be updated soon.<br><br>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                        <b>Judging Criteria</b><br>\n" +
                        "\n" +
                        "                                        - A robot would be victorious if its opponent is immobilized. <br>\n" +
                        "\n" +
                        "                                        - A robot will be declared immobile if it cannot display linear motion of at least one \n" +
                        "\n" +
                        "                                        inch in a timed period of 30 seconds. A robot with one side of its drive train disabled, \n" +
                        "\n" +
                        "                                        demonstrating some degree of controlled movement would not be considered. <br>\n" +
                        "\n" +
                        "                                        - A robot that is deemed unsafe by the judges after the match has begun will be \n" +
                        "\n" +
                        "                                        disqualified and therefore declared the loser. The match will be immediately halted \n" +
                        "\n" +
                        "                                        and the opponent will be awarded a win. <br>\n" +
                        "\n" +
                        "                                        - Robots cannot win by pinning or lifting their opponents. Organizers will allow pinning or lifting for a maximum of 15 seconds per pin/lift then the attacker robot will be instructed to release the opponent. If, after being instructed to do so, the attacker is able to release but does not, their robot may be disqualified. If two or more robots become entangled or a crushing or gripping weapon is employed and becomes trapped within another robot, then the competitors should make the timekeeper aware, the fight should be stopped and the robots separated by the safest means.  <br>\n" +
                        "\n" +
                        "                                        -  In case both the robots remain mobile after the end of the round then the winner will be decided subjectively.<br>\n" +
                        "\n" +
                        "                                        <br>\n" +
                        "\n" +
                        "                                        Points will be given on the basis of aggression (frequency, severity, boldness and effectiveness of attacks deliberately initiated), damage (through deliberate action), control, strategy (attack as well as defence) and crowd reaction. Further details would be put up soon!\n" +
                        "\n" +
                        "                                        Ultimately, the Judges decision would be final, binding and unquestionable.<br><br>\n" +
                        "\n" +
                        "                                        PS: All teams wishing to participate are requested to send an abstract to bphc.phoenix@gmail.com on or before 4th October 2014.<br><br>\n" +
                        "\n" +
                        "                                        <b>Contact Details: </b><br>\n" +
                        "\n" +
                        "                                        Chandra kiran 7731045306<br>\n" +
                        "                                        Sai Akhil 7730097555<br><br>\n" +
                        "                                        </p>\n",
                "",
                "http://bits-atmos.org/Events/img/robowars.jpg",
                0,
                0d,
                0);

        tableManager.addEntry(17,
                "Electrical",
                "Technical Events",
                "Line & Wall Follower",
                1234669l,
                1234998l,
                "",
                "<p style=\"font-size:20px;\"><b>EVENT DESCRIPTION</b><br>\n" +
                        "\n" +
                        "                                            It's time to test your basic automation and robotics skill! \n" +
                        "\n" +
                        "                                            A basic line follower robot is an automated vehicle capable of navigating on \n" +
                        "\n" +
                        "                                            ground using wheels/legs following a guided path using sensors,from a \n" +
                        "\n" +
                        "                                            starting line to a finish line on the track. <br><br>\n" +
                        "\n" +
                        "                                            <b>PROBLEM STATEMENT</b><br>\n" +
                        "\n" +
                        "                                            Design an autonomous robot, capable of following a guided path .The accuracy \n" +
                        "\n" +
                        "                                            of the bot in accomplishing the task, in minimum possible time will be the \n" +
                        "\n" +
                        "                                            judging factor.<br><br>\n" +
                        "\n" +
                        "                                            <b>BOT SPECIFICATIONS</b>\n" +
                        "\n" +
                        "                                            - The robot must be able to fit in a box of 25cmX25cmX30cm .<br>\n" +
                        "\n" +
                        "                                            - The robot should be completely autonomous. No radio/remote control is \n" +
                        "\n" +
                        "                                            permissible, i.e., the robot should follow the given track when left on its \n" +
                        "\n" +
                        "                                            own.<br>\n" +
                        "\n" +
                        "                                            - No outsourcing of battery/power source is allowed, i.e., the battery pack \n" +
                        "\n" +
                        "                                            should be included along with the body of the robot.<br>\n" +
                        "\n" +
                        "                                            - Any microcontroller/development board for the purpose of solving the \n" +
                        "\n" +
                        "                                            problem statement is allowed.<br>\n" +
                        "\n" +
                        "                                            - The maximum permissible weight of the robot is 10kg.<br>\n" +
                        "\n" +
                        "                                            - The bot shall undergo strict size and weight checks and may be disqualified \n" +
                        "\n" +
                        "                                            if it is found outruling the specifications.<br><br>\n" +
                        "\n" +
                        "                                            <b>GENERAL RULES</b><br>\n" +
                        "\n" +
                        "                                            - Open for students of all recognized institutes.<br>\n" +
                        "\n" +
                        "                                             \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                            1. <b>Round 1:</b> The robot should reach the ending point , along the suggested \n" +
                        "\n" +
                        "                                            path in the shortest time.<br>\n" +
                        "\n" +
                        "                                            2. <b>Round 2:</b> The robot should reach the ending point without colliding the \n" +
                        "\n" +
                        "                                            obstacles(height 25cm) placed along the path. The path can be continuous \n" +
                        "\n" +
                        "                                            or broken. The bot must have LED's to show if its on a brocken or a \n" +
                        "\n" +
                        "                                            continuous path.<br>\n" +
                        "\n" +
                        "                                            Further instructions will be disclosed during the competition.<br>\n" +
                        "\n" +
                        "                                            The path thickness in all the rounds is 3cm.<br>\n" +
                        "\n" +
                        "                                            - Time will be provided for the participents to make necessary changes in \n" +
                        "\n" +
                        "                                            their code between each round.<br>\n" +
                        "\n" +
                        "                                            - A robot cannot be imparted any momentum initially or anytime during the \n" +
                        "\n" +
                        "                                            course of the track-following act in that matter.<br>\n" +
                        "\n" +
                        "                                            - Any robot that loses the line course must reacquire the line at the point \n" +
                        "\n" +
                        "                                            where it lost contact or any preceeding point of the track .Any robot that \n" +
                        "\n" +
                        "                                            loses the line course and fails to reacquire it will be allowed a single \n" +
                        "\n" +
                        "                                            reattempt. The robot must start the course again from the beginning, and \n" +
                        "\n" +
                        "                                            if it loses the line course on its second attempt, it will be diqualified.<br>\n" +
                        "\n" +
                        "                                            - The timing for the completion of track shall be recorded by a stopwatch.<br>\n" +
                        "\n" +
                        "                                            - A bot that wanders off the arena boundries shall be disqualified. A bot shall \n" +
                        "\n" +
                        "                                            be deemed to have left the arena when one wheel/leg is found to be \n" +
                        "\n" +
                        "                                            completely outside the boundary drawn<br>\n" +
                        "\n" +
                        "                                            - The decision of the organisers regarding the above stated rules and the \n" +
                        "\n" +
                        "                                            conduct of the event shall be final and abiding.<br><br>\n" +
                        "\n" +
                        "                                            <b>CONTACT :</b><br>\n" +
                        "\n" +
                        "                                            Chandra Kiran : 7731045306 <br>\n" +
                        "\n" +
                        "                                            Raghu ram :  8187840685</p>\n",
                "",
                "http://bits-atmos.org/Events/img/mo_line%20follower.jpg",
                0,
                0d,
                0);

        tableManager.addEntry(19,
                "Electrical",
                "Technical Events",
                "Open Micro Challenge",
                1234669l,
                1234998l,
                "",
                "<p style=\"font-size:20px;\"><b>About the event:</b><br>\n" +
                        "\n" +
                        "                                            If microprocessor and microcontrollers are your toys on interest, this contest is made for you. \n" +
                        "\n" +
                        "                                            Combine your knowledge of digital electronics, microprocessors to create miracles and solve real life \n" +
                        "\n" +
                        "                                            problems. With ticking clock and all your skills put at test, work on your preferred microcontroller \n" +
                        "\n" +
                        "                                            platform and walk away with prizes this October. It's high time you gear up for the challenge, with all \n" +
                        "\n" +
                        "                                            your concepts at their best and programming skills tuned up to the level of professionals. We present \n" +
                        "\n" +
                        "                                            to all you Microprocessor fanatics the Open Micro Challenge! <br><br>\n" +
                        "\n" +
                        "                                            <b>Eligibility criteria- </b><br>\n" +
                        "\n" +
                        "                                            1) The competition is open only to students.<br>\n" +
                        "\n" +
                        "                                            2)The team may consist of at most three members. A student can't be part of more than one team. <br><br>\n" +
                        "\n" +
                        "                                            <b>Rules and Specifications:-</b><br>\n" +
                        "\n" +
                        "                                            The following is the format of Open Micro Challenge for this ATMOS :<br>\n" +
                        "\n" +
                        "                                            The entire event is divided into 3 rounds. The one who emerges out of the last round with the best \n" +
                        "\n" +
                        "                                            solution will have their hands on the trophy.<br>\n" +
                        "\n" +
                        "                                            <b>Round 1:</b> This a paper and pen quiz round, where your basics of the subject will be put under test \n" +
                        "\n" +
                        "                                            with time running out of your hand and confusing choices on paper. You and your team mates have to \n" +
                        "\n" +
                        "                                            outperform your counterparts to continue in the game.<br>\n" +
                        "\n" +
                        "                                            <b>Round 2 : </b> For all the programming geeks out there, this is your chance to get your fingers going on \n" +
                        "\n" +
                        "                                            solving some amazing microprocessor based problems. In a nut-shell what is expected of you in this \n" +
                        "\n" +
                        "                                            round is to write ASM codes to the problems given taking all the given requirements into \n" +
                        "\n" +
                        "                                            consideration.<br>\n" +
                        "\n" +
                        "                                            <b>Round 3:</b> Open up your minds for innovative nerdy ideas to tackle real life based problems. You \n" +
                        "\n" +
                        "                                            will given the problem statement on the spot and also all the equipment, you need to build a working \n" +
                        "\n" +
                        "                                            model to the desired specifications<br><br>\n" +
                        "                                            <b>Contact Details: </b><br>\n" +
                        "\n" +
                        "                                            Yashwanth:918466002375<br>\n" +
                        "                                            Bhargav: 96 18 058789\n" +
                        "                                            </p>\n",
                "",
                "http://bits-atmos.org/Events/img/openMicrochallenge.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(18,
                "Electrical",
                "Technical Events",
                "Circuit Art",
                1234669l,
                1234998l,
                "",
                "<p style=\"font-size:20px;\"><b>Event: </b> Always wanted to showcase your circuit designing skills? Brush them up a little because you \n" +
                        "\n" +
                        "                                            have a chance to. Or, did you ever think of solving real time problems with the stuff you learn in your \n" +
                        "\n" +
                        "                                            courses? Just make up your mind and analyse the real life problems. It'll help with a competition that \n" +
                        "\n" +
                        "                                            will be held during ATMOS'15.  Circuit Art is a competition where you can prove your circuit \n" +
                        "\n" +
                        "                                            designing skills. All you need to do is to put the stuff you learn in your courses together like the \n" +
                        "\n" +
                        "                                            Clocks, Flip flops, Oscillators, Amplifiers and many other digital components. <br><br>\n" +
                        "\n" +
                        "                                            Where ever you are down the line,  a Beginner or a Professional, this event is for you! <br><br>\n" +
                        "\n" +
                        "                                            <b>Eligibility Criteria: </b><br>\n" +
                        "\n" +
                        "                                            1. All the students can take part in the competition. <br>\n" +
                        "\n" +
                        "                                            2. It's a team event. A team might consist of maximum 3 members. A participant can be part \n" +
                        "\n" +
                        "                                            of only one team. <br><br>\n" +
                        "\n" +
                        "                                            <b>Procedure:</b><br>\n" +
                        "\n" +
                        "                                            Round 1:  It's a quiz which takes place for an hour. Go through all your basics because you\n" +
                        "\n" +
                        "                                            need to be really quick. Teams will be selected to the next round only on the basis of the score you get \n" +
                        "\n" +
                        "                                            in this round. Better Practice too! <br>\n" +
                        "\n" +
                        "                                            Round 2: Now that you are done with the quiz, here comes the part where you can show case \n" +
                        "\n" +
                        "                                            your circuit designing skills and gets your hands on real life problems. A problem statement will be \n" +
                        "\n" +
                        "                                            given which needs to be solved with the components provided by us. Any team cannot use the \n" +
                        "\n" +
                        "                                            components of their own. A better design will be judged on several technical parameters.  Better go \n" +
                        "\n" +
                        "                                            the labs in the D-Block and practice a little!<br>\n" +
                        "\n" +
                        "                                            After all, you'll be walking away with Cash Prizes.\n" +
                        "                                            <br><br>\n" +
                        "                                            <b>Contact Details: </b><br>\n" +
                        "                                            Pujitha:919010945849<br>\n" +
                        "                                            Gopi:955-398-3922\n" +
                        "                                    </p>",
                "",
                "http://bits-atmos.org/Events/img/circuitart.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(16,
                "Electrical",
                "Technical Events",
                "Quadcopter Challenge",
                1234669l,
                1234998l,
                "",
                "<h2>Overview</h2>\n" +
                        "                                   <p style = \"font-size:20px;\">Design and build a wireless remote controlled flying drone to complete the tasks with highest points possible.</br>There are two rounds and a BONUS round in the competition. Top 10 teams from ROUND-1 are eligible to compete in ROUND-2. There is an optional BONUS round to show your outstanding design, maneuver capabilities.</p>\n" +
                        "                                   <h2>Drone Specifications</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>Drone should fit in the box with dimensions 60cm x 60cm x 30cm.</li>\n" +
                        "                                        <li>Drones should be powered/propelled with non-hydrocarbon engines. </li>\n" +
                        "                                        <li>Flying machines which are eligible includes Quadcopters, Hexa/Octacopters, tricopters.</li>\n" +
                        "                                        <li>Participants must make all parts of the aircraft themselves. Usage of Ready-to-Fly (RTF) and Almost-Ready-to-Fly (ARTF) kits is strictly prohibited. However, the kit comprising of unassembled cut-pieces of Balsa wood is allowed. </li>\n" +
                        "                                        <li>Use of ready-made actuators/motors, remote controls and propellers are allowed.</li>\n" +
                        "                                        <li>Metal propellers are not allowed.</li>\n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "                                   <h2>ROUND-0</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>Participants have to submit a video of the working model on or before   8th October. +50 pts</li>\n" +
                        "                                        <li>The video should be an unedited clip, at least 1 minute in length, showing the aircraft taking off initially and taking a U-turn.</li>\n" +
                        "                                        <li>In the video, one of the participants from the team must demonstrate that he/she has decent flying skills for safety reasons.</li>\n" +
                        "                                        <li>The participants need to show their aircraft, the basic functions of their remote control, fly the aircraft with which they will be participating in the event and demonstrate its launch/take off, flight and landing. </li>\n" +
                        "                                        <li>The participants have to share their video clip at below given link:</br>pheonix@hyderabad.bits-pilani.ac.in</li>\n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "\n" +
                        "                                      <h2>ROUND-1</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>Participants are supposed to fly their drones in the path specified in figure below.</li>\n" +
                        "                                        <li>Here's the scoring for each sub-task:\n" +
                        "                                            <ul>\n" +
                        "                                                <li>Steadily takeoff from the starting point until your drone reaches a height of 20ft* : +30 pts</li>\n" +
                        "                                                <li>Move forward towards the flag post which is located 30ft away : +45 pts</li>\n" +
                        "                                                <li>Rotate your drone 180o clockwise (as observed from above) while maintaining the same x, y, z coordinates : +50 pts</li>\n" +
                        "                                                <li>Move towards the starting position : +45 pts</li>\n" +
                        "                                                <li>Soft land your drone to complete the ROUND-1: +30 pts</li>\n" +
                        "                                                <li>Crossing the arena in any case will fetch a penalty of 35pts.</li>\n" +
                        "                                            </ul>\n" +
                        "                                        </li>\n" +
                        "                                        <li>Each team can get a maximum of 200 points in ROUND-1.</li>\n" +
                        "                                        \n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "                                   <p style = \"font-size:20px;\">* All vertical measurements are subject to be in range of +/- 3ft</p>\n" +
                        "\n" +
                        "                                    <h2>ROUND-2</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>Top 10 teams from ROUND-1 are eligible to compete in ROUND-2 where you are supposed to fly your drones in the path specified in the figure below.</li>\n" +
                        "                                        <li>Scoring system for sub-tasks in this round:\n" +
                        "                                            <ul>\n" +
                        "                                                <li>Steadily takeoff from the starting point until your drone reaches a height of 20ft* : +30 pts</li>\n" +
                        "                                                <li>Move forward towards the flag post which is located 30ft away : 4 * (+30 pts)  </li>\n" +
                        "                                                <li>Decrease/Increase the altitude of your drone while maintaining the same x, y coordinates : 4 * (+30 pts) </li>\n" +
                        "                                                <li>Rotate your drone 90o clockwise (as observed from above) while maintaining the same x, y, z coordinates : 3 * (+30 pts) </li>\n" +
                        "                                                <li>Rotate your drone 135o and soft land your drone in the center of arena to successfully complete the Round : +40 pts</li>\n" +
                        "                                                <li>Crossing the arena in any case will fetch a penalty of 35pts.</li>\n" +
                        "                                            </ul>\n" +
                        "                                        </li>\n" +
                        "                                        <li>Each team can get a maximum of 400 points in ROUND-2.</li>\n" +
                        "                                        \n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "                                   <p style = \"font-size:20px;\">* All vertical measurements are subject to be in range of +/- 3ft</p>\n" +
                        "\n" +
                        "\n" +
                        "                                   <h2>BONUS round</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>This is an optional BONUS round where the participants are required to mount a camera on your drone and record a video of whole arena for a minimum of one minute.</li>\n" +
                        "                                        <li>Video should be automatically saved on on-board memory and should be viewable on any computer after retrieving the drone.</li>\n" +
                        "                                        <li>Scoring is based on the following parameters - stabilization of video, video resolution, recorded time.</li>\n" +
                        "                                        <li>Scoring is relative and a team can get a maximum of 450 points in this round. </li>\n" +
                        "\n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "\n" +
                        "                                   <h2>Judging</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>Teams will be judged based on Scoring, Design, Construction, Technology used.</li>\n" +
                        "                                        <li>Scoring of mentioned path will be: Score = Surprise attribute + points earned - penalty.</li>\n" +
                        "                                        <li>Surprise attribute will be disclosed while evaluating, after the event.</li>\n" +
                        "                                        <li>Decision made by judges is final. </li>\n" +
                        "\n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "                                    <h2>Team Specifications</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>A team can consist of a maximum of 6 participants. Teams may consist students from different educational institutions.</li>\n" +
                        "                                        <li>A participant cannot be a part of two different teams.</li>\n" +
                        "                                        \n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "                                   <h2>Eligibility</h2>\n" +
                        "                                   <p style = \"font-size:20px\">All students with a valid identity card of their respective educational institutions are eligible to participate in the event.</p>\n" +
                        "\n" +
                        "                                    <h2>Certificate Policy</h2>\n" +
                        "                                   <ul style = \"font-size:20px;\">\n" +
                        "                                        <li>Certificate of excellence will be awarded to the top three teams.</li>\n" +
                        "                                        <li>Certificate of Participation will be awarded to the teams who have successfully completed the ROUND 1.</li>\n" +
                        "                                        \n" +
                        "                                   </ul>\n" +
                        "\n" +
                        "                                   <h2>Conatct Details:</h2>\n" +
                        "                                   <p style = \"font-size:20px;\">Surya teja:99 12 214929</br>\n<br>" +
                        "                                    Chandra kiran: 7731045306</p>",
                "",
                "http://bits-atmos.org/Events/img/quadcopter.jpg",
                0,
                0d,
                0);

        tableManager.addEntry(21,
                "Electrical",
                "Technical Events",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<h4>Date of Presentation:11th October</h4>\n" +
                        "                                        <h2>PROBLEM STATEMENT</h2>\n" +
                        "                                        <p style = \"font-size:20px;font-weight:normal\">An opportunity to exhibit your ideas and work of research on a higher platform.</p>\n" +
                        "\n" +
                        "                                        <h2>GUIDELINES</h2>\n" +
                        "                                        <ul style = \"font-size:20px;font-weight:normal\">\n" +
                        "                                            <li>The entire paper (not exceeding 6 pages) along with the abstract (of 2 pages maximum) must be mailed to phoenix@hyderabad.bits-pilani.ac.in with the subject \"Paper presentations-15\", latest by October 4, 23:59 hours.</li>\n" +
                        "                                            <li>The topic under which the paper is being submitted should be mentioned at the top followed by the title of the paper, name(s) of the team member(s), and name of the college and phone number.</li>\n" +
                        "                                            <li>The document/paper must be in Times New Roman or Arial font, with a font size NOT less than 12, and line spacing not less than 1.15.Participants are requested to adhere to the IEEE format for submission of the paper.</li>\n" +
                        "                                            <li>Teams that are shortlisted for next round will be announced by 7th October. </li>\n" +
                        "                                            <li>Selected papers will proceed to the presentation round on 10th or 11th October and the participants must present their paper in the form of a power point presentation.</li>\n" +
                        "                                            <li>Each team will be given 7 minutes to present their topic and this will be followed by a 3 minute question answer session.</li>\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                        <h2>TOPICS</h2>\n" +
                        "                                        <p style = \"font-size:20px;font-weight:normal\">The following are few topics for Paper Presentations in Atmos'15. Topics other than these can also be presented.</p>\n" +
                        "                                        \n" +
                        "                                        <ul style = \"font-size:20px;font-weight:normal\">\n" +
                        "                                            <li>Electronics and Instrumentation</br><ul>\n" +
                        "                                                <li>Low power VLSI</li>\n" +
                        "                                                <li>Embedded Systems for Bio-Medical Applications</li>\n" +
                        "                                                <li>Embedded Systems for Automation</li>\n" +
                        "                                                <li>Integrated Circuit Design (Digital and Analog)</li>\n" +
                        "                                                <li>Hardware for Signal Processing</li>\n" +
                        "                                                <li>Emerging Technologies in Electronics</li>\n" +
                        "                                                <li>DSP based instrumentation</li>\n" +
                        "                                                <li>Parameter Estimation</li>\n" +
                        "                                                \n" +
                        "                                            </ul></li>\n" +
                        "                                            <li>Communications field</br><ul>\n" +
                        "                                                <li>MIMO-OFDM</li>\n" +
                        "                                                <li>OFDMA</li>\n" +
                        "                                                <li>Heterogeneous Networks</li>\n" +
                        "                                                <li>MANETS (Mobile ADHOC Networks----Routing Protocols in MANETS)</li>\n" +
                        "                                                <li>SDR (Software Defined Radios) or Reconfigurable Radios</li>\n" +
                        "                                                <li>Energy Harvesting in Wireless Communications</li>\n" +
                        "                                                <li>Energy Harvesting in Wireless Sensor Networks</li>\n" +
                        "                                                <li>BANS (Body Area Networks)</li>\n" +
                        "                                                <li>Cooperative Communications in Wireless Networks</li>\n" +
                        "                                                <li>LTE or LTE Advanced</li>\n" +
                        "                                                <li>QOS and Resource Management</li>\n" +
                        "                                                <li>Sensor Networks and Embedded Systems</li>\n" +
                        "                                                <li>MIMO Communications in Computer Networks</li>\n" +
                        "                                                <li>RFID Networks and Protocols</li>\n" +
                        "                                                <li>Mobile and Wireless Security</li>\n" +
                        "                                                <li>Smart Antennas</li>\n" +
                        "                                                <li>WIMAX</li>\n" +
                        "                                                <li>Routing algorithm for specialized long-lifetime pico-radio networks</li>\n" +
                        "                                                <li>Efficient Algorithms for Spectrum Sensing</li>\n" +
                        "                                                <li>FEMTO Cells</li>\n" +
                        "                                                <li>Enhancing optical communications with next generation fibers and associated fiber components</li>\n" +
                        "                                                <li>MC-CDMA </li>\n" +
                        "                                                <li>WCDMA</li>\n" +
                        "                                                <li>Personal satellite communication: technologies and challenges</li>\n" +
                        "                                                <li>Broadband Wireless Communication</li>\n" +
                        "                                                <li>Error Control for Network-Coded Transmission</li>\n" +
                        "                                                <li>Structured Low-Density Parity-Check Codes and Associated Encoding/Decoding Algorithms</li>\n" +
                        "                                            </ul></li>\n" +
                        "                                            <li>Electrical field </br><ul>\n" +
                        "                                                <li>Energy Conservation</li>\n" +
                        "                                                <li>Renewable energy sources</li>\n" +
                        "                                                <li>Power factor improvement</li>\n" +
                        "                                                <li>Microgrid</li>\n" +
                        "                                                <li>Power Electronics- FACTS</li>\n" +
                        "                                                <li>Brushless DC machines</li>\n" +
                        "                                                <li>Permanent Magnet Motors</li>\n" +
                        "                                                <li>Hybrid Electric Vehicles</li>\n" +
                        "                                                <li>High Voltage DC Transmission</li>\n" +
                        "\n" +
                        "                                            </ul></li>\n" +
                        "\n" +
                        "                                        </ul>  \n" +
                        "                                        <p style = \"font-size:20px;font-weight:normal\">For further details: <a href = \"https://www.facebook.com/events/771008563026079/\">Paper Presentation - ATMOS '15</a></p>\n" +
                        "                                        <h4>Contact Details:</h4>\n" +
                        "                                        <p style = \"font-size:20px;font-weight:normal\">Jaya Sahithi- 8096482023</br><br>Rachit-9948890407</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion2%20(1).jpg",
                0,
                0d,
                0);
        tableManager.addEntry(20,
                "Electrical",
                "Technical Events",
                "Digital Electronics Quiz",
                1234669l,
                1234998l,
                "",
                "<p style=\"font-size:20px;\">A brainstorming event and a plethora of quizzical questions for those that love electro-digital mechanisms. Here the one with the head of a whale wins the game. So gear up for a quiz bonanza.\n" +
                        "                                                               <br><br>\n" +
                        "                        <b>Contact Details: </b><br>\n" +
                        "                        Yashwanth:918466002375<br>\n" +
                        "                        Pujitha:919010945849                            \n" +
                        "                            </p>",

                "",
                "http://bits-atmos.org/Events/img/DigitalElectronicsQuiz.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(75,
                "Electrical",
                "Technical Events",
                "iNavigate",
                1234669l,
                1234998l,
                "",
                "<h2>Objective</h2>\n" +
                        "                                                    <p style = \"font-size:20px\">Teams are supposed to design and build an accelerometer based hand gesture controlled (manually controlled) robot and maneuver it with agility through the arena.</p>   \n" +
                        "                                                    <h2>Arena Description:</h2>\n" +
                        "                                                    <p style = \"font-size:20px\">ROUND I: </br>The arena for round one will be a simple track. The bot is supposed to navigate through the track without touching the side walls on the path. Touching the sidewalls will carry negative points. The distance between the sidewalls will be 40cm (5% error).</p>" +
                        "                                                    <p style = \"font-size:20px\">ROUND II: </br>The arena of round two will also be consisting of uneven surfaces, elevations (wedges), depressions and some other surprises which are kept for the event. The specifications of the wedges and depressions (height and angle of inclination) will be soon put up. The actual Layout of the arena will be shown 60 minutes before the commencement of the contest.</p>\n" +
                        "                                                    <h2>Robot Specifications & Rules</h2>\n" +
                        "                                                    <ul style = \"font-size:20px;\">\n" +
                        "                                                        <li>The Robot should conform to the maximum dimensions 25cm X 25cm X 25cm (l X b X h).</li>\n" +
                        "                                                        <li>It should not damage the game field and should not leave a trail behind to mark its path. It may be disqualified for adversely damaging the game field.</li>\n" +
                        "                                                        <li>Only one Robot is allowed per team.</li>\n" +
                        "                                                        <li>Team members are not allowed to touch the Robot once the game begins.</li>\n" +
                        "                                                        <li>No trials will be given before the beginning of the competition.</li>\n" +
                        "                                                        <li>The total weight of the Robot to be used in the game field must not exceed 5kg (10% tolerance).</li>\n" +
                        "                                                        <li>The robot could be either wired or wireless, though wireless robots are given bonus points as mentioned below.</li>\n" +
                        "                                                        <li>All the robots should be powered by an on-board supply.</li>\n" +
                        "                                                        <li>Maximum voltage between any two points should not exceed 12V.</li>\n" +
                        "                                                        <li>Teams should be self-equipped with charged batteries. In any case, AC power supply will not be provided by the organizers.</li>\n" +
                        "                                                        <li>Teams have to take a wise decision regarding wireless communication. Organizers will not be responsible for any kind of interference. </li>\n" +
                        "                                                        <li>Any robot that is unable to finish the task will be given just a single re-attempt. The robot has to re-start from the starting point during the re-attempt, failing which it will be disqualified.</li>\n" +
                        "                                                        <li>Time will be recorded by the judges. Time is recorded from the moment the robot starts from the starting position until the time it reaches the end of the maze.</li>\n" +
                        "                                                        <li>In any case of disputes, the decision of the judges will be considered final.</li>\n" +
                        "                                                        <li>Failure to comply with any of the above rules will lead to disqualification by the organizers.</li>\n" +
                        "                                                    </ul>   \n" +
                        "                                                    <h2>Judging Criteria</h2>\n" +
                        "                                                    <p style = \"font-size:20px;\">Time and perfection in navigating are of importance in the 1st round. Each team will be having a total of 600 points in the beginning. Team Score will be calculated with the following relation:</br>Score = 600 - (time taken (in sec.) to complete the arena) - 5 X (number of times the bot collides with the side walls).</br>NOTE: Teams which make a wireless robot will be awarded 50 bonus points.</br>Top teams will qualify for the 2nd round of the competition.</br>The same rules are applicable for the score calculations in the 2nd round also.</br></p>\n" +
                        "                                                    <h2>Contact:</h2>\n" +
                        "                                                    <p style = \"font-size:20px;\">Anil Kumar: +91-9705933771</br><br>Jayanth: +91-9705782828</p>",
                "",
                "http://bits-atmos.org/Events/img/inavigate.jpg",
                0,
                0d,
                0);

        tableManager.addEntry(46,
                "Chemical",
                "Technical Events",
                "Cannon Wars",
                1234669l,
                1234998l,
                "",
                "<h2>About The Event</h2>\n" +
                        "                                 <p style = \"font-size:20px;\">Are you someone who likes to mess with chemicals? Are you one of those who skirt the line between dangerous and cool?</br>If, YES, then you are one we are looking for! Come and participate in this monster of an event.</br>The event holds absolutely no prerequisites, other than you designing a cannon of course.  Design an effective cannon that uses only chemical reactions, to provide enough thrust to project a projectile.&#8232;</br></br>The event has three rounds.</br>Round 1: The range of your cannon will be tested in this round. The longer the better.&#8232;</br>Round 2: The accuracy of the cannon will be tested. Can you hit the bull's eye? </br>Round 3: The castle siege round. You would be pitted against another team, where in the first team to demolish the other teams castle would win. The more power you generate, the better are your chances.</br></br></p>\n" +
                        "                                 <h2>Rules:</h2>\n" +
                        "                                 <p style = \"font-size:20px;\"> 1. Participation will be in teams of 3.&#8232;</br> 2. No hazardous chemical reactions are allowed. (Let's leave the dangerous stuff to Dexter!)</br></p>\n" +
                        "\n" +
                        "                                 <h2>Contact Details:</h2>\n" +
                        "                                 <p style = \"font-size:20px;\">Yash Z Lata - 9529229820</br><br>Tauras Marwaha - 8499001706</p>",
                "",
                "http://bits-atmos.org/Events/img/cannonWars.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(44,
                "Chemical",
                "Technical Events",
                "Scrutinizing Catastrophe",
                1234669l,
                1234998l,
                "",
                " <p >Chemical hazards are threats to people and life-support systems that arise from the mass production of goods and services. When these threats exceed human coping capabilities or the absorptive capacities of environmental systems they give rise to industrial disasters. </br>Industrial hazards can occur at any stage in the production process, including extraction, processing, manufacture, transportation, storage, use, and disposal. Losses generally involve the release of damaging substances or damaging levels of energy from industrial facilities or sophisticated equipment's into surrounding environments. This usually occurs in the form of explosions, fires, spills, leaks, or wastes. Releases may occur because of factors that are internal to the industrial system (e.g. engineering flaws) or they may occur because of external factors (e.g. extremes of nature).</br>Consider yourself intelligent? Have deduction skills to flaunt upon? Love messing around with chemical machines? Have eyes which itch to absorb every detail?</br>There's a job waiting for you. Investigate notorious chemical process disasters, deal with equipment failures, operator errors and overloaded security systems, and try your best to prevent mishaps, turn in your analysis and save the day!</br>This ATMOS, step into total chaos, burn some brain cells figuring out complex equipment. Could you have done better?</br>They don't call you the process safety analyst for nothing, do they?</p>\n" +
                        "\n" +
                        "                                 <p >There are no disasters, only opportunities. And, indeed, opportunities for fresh disasters. </br>- Boris Johnson</p>\n" +
                        "\n" +
                        "                                 <h2>Rules:</h2>\n" +
                        "\n" +
                        "                                 <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>The event is a Two-round based event, with each report judged upon by the faculty. </li>\n" +
                        "                                    <li> There are 3 cases in the round 1 and 2 in the round 2.</li>\n" +
                        "                                    <li>Participation is in teams of two, so bring someone brave and dependable. Adequate information on everything relevant will be provided.</li>\n" +
                        "                                 </ul>\n" +
                        "\n" +
                        "                                 <p style = \"font-size:20px;\"> Winners will be chosen based on the efficiency of the solution and number of cases solved.</p>\n" +
                        "\n" +
                        "                                 <h2>Contact Details:</h2>\n" +
                        "                                 <p style = \"font-size:20px;\">Shakti Prasad Padhi - 9912258872</br>Santosh Raju V V - 9966953366</br></p>",
                "",
                "http://bits-atmos.org/Events/img/hazardousInvestigation.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(45,
                "Chemical",
                "Technical Events",
                "Chem Quiz",
                1234669l,
                1234998l,
                "",
                " <p style=\"font-size:20px;\">Do you play Quiz up? Do you love solving puzzles? Are riddles your thing? Then well, this ATMOS, ACE has a treat for your brains. We bring to you the most intriguing quiz of the season. </p>\n" +
                        "                                                <h2>The CHEM QUIZ.</h2>\n" +
                        "                                                <p style = \"font-size:20px;\">Don't worry, this quiz does not require you to have any prerequisite knowledge in chemical engineering. The quiz aims at testing the logic and implementation of methods provided to the participants.</p>\n" +
                        "                                                <h2>RULES:</h2>\n" +
                        "                                                <ul style = \"font-size:20px;\">\n" +
                        "                                                    <li>Participation will be in teams if two (single participant is also allowed).</li>\n" +
                        "                                                    <li>Standard quiz rules apply.</li>\n" +
                        "                                                    <li>The quiz will be held in two rounds.</li>\n" +
                        "                                                </ul>\n" +
                        "                                                <p style = \"font-size:20px;\">So, be our guest and witness a whole world of riddles and puzzles, the chemical way!</p>\n" +
                        "                                                <h2>CONTACT DETAILS:</h2>\n" +
                        "                                                <p style =\"font-size:20px;\">Santosh Raju V V - 9966953366</br><br>Tauras Marwaha - 8499001706</p>",
                "",
                "http://bits-atmos.org/Events/img/CHEMICALQUIIZ.jpg",
                0,
                0d,
                0);

        tableManager.addEntry(1,
                "Mechanical",
                "Technical Events",
                "Mini-GP",
                1234669l,
                1234998l,
                "",
                "<h2>Overview</h2> <p>I bet most of us have dreamt of racing our cars at top speeds through spick, black race courses. Come October and BITS-Pilani,Hyderabad Campus will be all set to fulfil your dream,well,in a way.On the line of the ecstatic Moto GP competition,we are conducting The Mini GP competition.The competition will have its participants (or racers) drive self-designed remote controlled cars through deliciously twisty race tracks to win the final cup! Seems simple?Well, brace yourselves for some rock solid competition. Forget not, the more innovative and inventive your design is, the closer you are to the Mini GP cup.Fasten your seatbelts and let the adrenaline pump up!</p> <h2>Problem Statement</h2> <p>The participants are expected to race their remote control based IC engine cars through tracks whose complexity is bound to increase level after level.The teams completing the laps in the best possible time will qualify for a grand final race.The design skills and application of real-world driving skills to the miniature cars are put to test. </p> <h2>Competition Rules </h2> <p></p><ul><li>    The track will have check points at regular intervals.</li> <li>    In case a machine tumbles, or halts, or goes off the arena at any point on the track, only one of the participants is allowed to lift it up and place it at the nearest checkpoint behind that point. (Previous two rules are for non-racing rounds. The rules for racing round will be told on the spot)</li> <li>Until and unless there is a need to touch the vehicle as stated above, they have to be fully remot3e controlled throughout the rave.</li> <li>Every time the machine requires lifting by the team member, a time-penalty will be imposed.</li> <li>Any vehicle is not allowed to leave any disintegrated part on the race track amidst of the race. In case this happens, the team will be disqualified.</li> <li>The teams are also not allowed to damage the opponents vehicle deliberately. If found guilty, the accused team will be disqualified.</li> <li>There will be a time-penalty for reverse gearing.</li> <li>Teams may consist of a maximum of 5 members.</li> </ul> <p></p> <h2>Judging Criteria</h2> <p></p><ul><li>Participants are expected to design their own racing car. However, in case the participants use readymade cars, they will only be evaluated for 3/4th of the total marks. That is a penalty if 1/4th of the total marks will be levied on them. If their car is part self-designed and part readymade, then the marks will be given accordingly as per the judge's discretion. Above mentioned penalty is not applicable for the final round. </li><li>Driving ability and application skills of the participant will also be put to test. </li> <li>Lap time will be another criterion to decide the winner. </li></ul><p></p> <h2>Vehicle Specifications</h2> <p></p><ul><li>The vehicle should be designed so that it fits in an 800mm X 700 mm box at every given point of time in the race. This is excluding the remote controller.</li> <li>It is essential that the car be designed with an internal combustion engine (IC engine). Motors etc. should not be the main driving sources of the car. However, for steering mechanisms or any other mechanism apart from propulsion DC motors and servos can be used. </li> <li>Only one IC engine should be used in the vehicle. Use of any other sources such as chemicals, compressed gas, rockets etc. for propulsion is not allowed. The allowed capacity of IC engine to be used is 4.5 cc or less. </li> <li>The vehicle has to be necessarily controlled by a wireless remote control system throughout the competition. The vehicle must have two remote controllers of different frequencies or an alternate frequency remote control circuit which can be switched to either frequency before the start of the game. This is done to avoid frequency interference between the two competing vehicles. </li> <li>Remote control systems from toys or the ones purchased from a market might be used without any fear of penalization. </li> <li>The vehicle parts can be ready made. But there will be extra points if you make chassis and steering mechanism by yourself (See judging criteria for more detail).If you are making your car then other functional parts like motors and servos, gears, springs, engine, remote control systems, batteries, wheels, braking mechanism are allowed to be used as directly available from the market. </li> <li>It is essential that the vehicle has an onboard power supply to provide electricity to any mechanism requiring electric power. </li><li>Electric voltage not more than 12V should be used in the machine wherever required. </li><li>Participants are advised to use a proper cooling mechanism to prevent overheating of the engine. </li> <li>Verify with the organizers if you have the slightest doubt whether a component can be used or not. Please do not hesitate. Designs not complying with the vehicle specifications will be disqualified. </li></ul><p></p> <h2>General Rules</h2> <p></p><ul> <li>Depending on the number of teams participating and other time constraints, the arena will be given to the participants for practicing. </li><li>The time slots will be given on the basis of first come first serve basis, but little duration for practicing is assured to everyone. </li><li>Each team must come up with its own, unique team name. </li><li>Organizers reserve the right to change any of the above rules. </li><li>articipants can address their queries indirectly via e-mail or forums and directly by calling up the organizers. </li><li>For help and reference, some material will be uploaded soon. </li><li>Organizers and judges decision shall be considered as final and binding on all. </li></ul><p></p> <h2>Eligibility</h2> <p>Each participant must be a full time student enrolled in an undergraduate or post graduate program from any recognized university or college. Any professional body other than student are not allowed in this competition. </p> <h2>Registration</h2> <p></p><ul>Participants have to submit an abstract giving the complete description of their vehicles based on the following lines. <li>Teams that fabricate their own vehicle must explain the steering mechanism and the chassis layout in detail, along with proper illustrations. Picture(s) showing all the three should be attached.</li> <li>The participants are required to send the photographs of the chassis before assembly so as to ensure that the parts of the car have been built by themselves, that being the case.</li> <li>Photograph of the car in the current state must also be sent with the name of the photograph as the date on which it is taken.</li> <li>Specification of all other components like the engine, remote controllers etc must be specified.</li> <li>The complete abstract along with the photos and the team name and members name.</li> <li>If readymade cars are used by the team then the team should mail the exact specification of the car.</li> <li>Write the name of team along with the name of team leader and team members with contact numbers and e-mail ids.&nbsp;{put contact details here and the email to which they have to send their design illustrations}</li> <li>A confirmation mail will be sent to the Team leaders.</li></ul> <p></p> <h4>Contact:</h4> <p>Mithilesh: +91-8466095758</p>",
                "",
                "http://bits-atmos.org/Events/img/miniGp.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(2,
                "Mechanical",
                "Technical Events",
                "Junk Yard Wars",
                1234669l,
                1234998l,
                "",
                "<h2>About The Event</h2><p>The participants will be given random scrap material (the only inventory) out of which you have to build a working model (machine) which can perform the specified task. It's a test of your dexterity and intuitive thought process.The competition aims to test how one quick and creative one can be at the same time and solve a problem. Thats Junkyard Wards is about. </p> <h2>Problem Statement</h2> <p>Every team has to conceptualize, design and build a machine in limited time to compete in a specified task given to them on the spot. </p> <h2>Event Structure</h2> <p> The event will consist of a preliminary elimination round followed by the main event.There will be an elimination round.So everyone has to report at the venue 30 minutes before the commencement of the preliminary elimination round. <br><b>Preliminary Elimination Round:-</b>There will be a quiz which may contain both objective and subjective questions. The questions will test the students engineering aptitude and to make understand its significance. The duration of the quiz will be a 1 hour. Only  6 teams will be selected for the main event. <br><b>Main Event:- </b>The selected teams will be given 4hrs (duration may subject to change if the organising body decides so) to work out their innovations and build a perfect solution to the given problem in the given time and resources. The problem statement will be mentioned on the spot. </p> <h2>Rules</h2> <p></p><ul> <li>Students from different institutes can form a team. </li><li>A team may consist of 3 to 5 members. </li><li>Every participant must get himself/herself registered before the event begins. </li><li>For eliminations, team have to appear for a test as single entity. Each team will be given 1 question paper and 1 answer sheet along with rough sheet. </li><li>Marks of the preliminary round will not be carried to the main event. </li><li>Use of any parts or instruments other than provided by the organisers is prohibited. </li><li>Time for the 2nd round will start soon after the task to be performed is declared. </li><li>Each team must formulate their own ideas and designs. Any form of plagiarism will lead to disqualification. </li><li>There will be no restriction on the type of mechanism used by the team to perform the task provided no other rule is violated. </li><li>No extra time will be given to any team under any circumstances. </li><li>Organisers are not responsible for any loss of property, injury and delays during the event. </li><li>Judges decision will be final and binding on all. </li><li>The team must adhere to the team spirit &amp; accomplish a healthy competition. </li><li>Any negligence/fraud on the part of the participating team, once detected, will lead to immediate disqualification. </li><li>Organisers reserve the right to change any number of rule and the team must abide to it. </li><li>Participation certificates will be provided to all the participants. </li><li>Participants are required to stay up to date with the website. </li></ul><p></p> <h2>Judging Criteria</h2> <p></p><ul><li>The quiz will be set by the panel of professors from the various engineering departments. </li><li>The same panel will check the quiz paper and the results declared by them are final and no request for reconsideration will be entertained.<p></p> <h3><b>PRIZES:-</b> The team that comes up with the most Innovative and Creative design will walk away with exciting CASH PRIZES. </h3> </li></ul> <h4>Contact:</h4> <p>Mithilesh: +91-8466095758</p>",
                "",
                "http://bits-atmos.org/Events/img/junkyardWars.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(3,
                "Mechanical",
                "Technical Events",
                "Prodigal Designer",
                1234669l,
                1234998l,
                "",
                "<h2>About The Event</h2><p> Do you have a passion for designing? Are you waiting for an opportunity to flex your CAD skills? This Atmos we bring you Prodigal Designer, an exclusive CAD based designing event. Get ready to let your imagination soar and hone your skills! </p> <h2>Problem Criteria</h2> <p>The participants have to model in proEngineer software. It consists of two rounds, the first round will test the participants accuracy and fastness. The second round is a spot round, its problem statement will be announced on the day of the event. </p> <h2>Rules</h2> <p></p><ul><li>The participants have to design only on proE software.</li> <li>The coordinators reserve the right to disqualify a participant in case of any misbehaviour.</li> <li>In case of any disputes/discrepancies, the decision of the organisers will be final and binding.</li></ul> <p></p> <h2>Judging Criteria</h2> <p></p><ul><li>In first round the participants have to draw as many elements(ex- screw, gears etc.) as possible within the time limit.</li> <li>Participants will be judged based on the quality and number of elements they are able to draw. </li><li>Second round is a spot round. </li></ul><p></p> <h4>Contact:</h4> <p>Mithilesh: +91-8466095758</p>",
                "",
                "http://bits-atmos.org/Events/img/prodigalDesigner.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(4,
                "Mechanical",
                "Technical Events",
                "Paper Presentation",
                1234669l,
                1234998l,
                "",
                "<h2>About</h2> <p>The Paper Presentation Event organized by the Mechanical &amp; Manufacturing Engineers Association of BITS Pilani, Hyderabad campus aims to provide the best platform to showcase your research &amp; ideas in front of experienced faculty members and chief guests. What more? Prepare a presentation, unleash the speaker in you and bag exciting prizes.</p> <h3>Problem Statement</h3> <p>Present your idea in the simplest possible way. Innovative ideas will be honored with deserving rewards.</p> <h3>Format</h3> <p>The event will be conducted in three rounds, sequential, which implies teams completing first round will move on to second, and so on.</p> <br><b>Round 1</b><br>Interested applicants must first send in their presentation along with proper abstract to&nbsp;bphc.mea@gmail.comThe presentations must follow the following format: <ul><li>The paper should be written in Times New Roman </li><li>Font size should be 12 pt. </li><li>Line spacing should be 1.15 </li><li>The text alignment should be Justified. </li></ul>All the presenters are expected to follow the above mentioned code.Presentations can also be done in teams.The topic of the presentation must strictly lie in the bounds of mechanical department. Subjects deviating largely will be rejected. <br><b>Round 2</b><br>Entries selected in the first round will be invited for second round through mail.The second round will be held in October at BITS Pilani, Hyderabad Campus.Presentations should be given on the same topic that are sent for first round.Interactive Q@A round will follow the presentation.The Maximum allowed time for presentation will be in formed in further mail. <br><b>Round 3</b><br>Further moving on will be the third and final round.The third round will be held in October at BITS Pilani, Hyderabad Campus.Same rules of second round will be followed.The Maximum allowed time for presentation will be informed in further mail. <h2>General guidelines and rules</h2> <p> </p><ul><li>A team may consist of a maximum of 4 members. </li><li>The scrutiny will be done by experienced faculty, and any form of plagiarism will be rejected. </li><li>Participants should abide by topics given for second round. </li><li>The last date for submission of entries will be in October. Entries submitted later will not be entertained. </li><li>Participants selected for second round will be informed through mail. </li><li>Decision of the organizers will be final in all cases.</li></ul> <p>Last Date for Submission of Abstract is 4th October 11:59 P.M</p> <h4>Contact:</h4><br> <p>Aamir Jowher-9010708214</p>",
                "",
                "http://bits-atmos.org/Events/img/paperPresentaion.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(76,
                " ",
                "Others",
                "BITSMUN",
                1234669l,
                1234998l,
                "",
                " <p>BITSMUN 2015, the annual Model United Nations Conference conducted by BITSMUN 2015 is all set to be bigger and better this year with an amazing chief guest, a multi-cultural environment and engaging debates. Suit up to be a part of this dialogue and hone your leadership, debating and inter-personal skills, with lucrative prizes to be won!</br></br>As a representative of a country at a prestigious gathering, face new challenges and steer your way through difficulties, with diplomacy and tact to aid you. Whether you are a newbie or an experienced MUNer, if you are looking for intensive and quality discussion on international issues, BITSMUN '15 is where you should be.</br></br></p> <h2>For further details:</h2><br><a href=\"http://bitsmunhyd.in/\"><p style=\"font-size:20px\">http://bitsmunhyd.in/</p></a>",
                "",
                "http://bits-atmos.org/Events/img/BItsmun-01.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(34,
                " ",
                "Others",
                "ENIGMA",
                1234669l,
                1234998l,
                "",
                "     <p>The ground is scorched and the warriors are ready. Battle it out with the best in country in a quiz par excellence, designed to ensure only the toughest shall survive. Regionals held all across India with the Hyderabad regionals and the National Finals held during ATMOS. </br>With Quizmaster Vikram Joshi coming over for the Final leg of this journey, only the greatest shall ever get the prize. Suit up and get ready for the ultimate grilling on all topics General as we bring to you the Greatest Quiz that ever is. Prizes worth 50K up at stake.</p> <h2>Hyderabad Regionals</h2> <p>It will be a standard general quiz which will be held in two parts prelims and finals.</br></br>The top 2 teams from Hyderabad join the winners from the other 7 cities in the National Finals. (i.e. Mumbai, Pune, Chennai, Pilani, Goa, Bangalore and Kolkata). </p> <h2>National Finals</h2> <p>Top teams from all the regionals battle out for the Grand Prize immediately after the Hyderabad Regionals. </p> <h2>Event Rules:</h2> <ul> <li>Teams of two are allowed.</li> <li>The top 8 teams will advance to the finals.</li> <li>The final top three teams will get prize money</li> <li>Use of Internet is strictly prohibited. If caught, you will be disqualified immediately.</li> </ul> <h2>Eligibility Criteria:</h2> <p>This quiz is open to all and the only eligibility is that you must be present there.</p> <h2>Judging Criteria:</h2><p >Standard quiz rules will be followed and the team with maximum points will be ranked first and so on.</br>In case of any controversy, the decision of the quizmaster will be final.</p> <h2>Registration:</h2> <p>Registration can be done online or on-spot by paying a minimal fee of Rs.0.00 only</p> <h2>Contact:</h2> <p>Komal 7729987363</p>",
                "",
                "http://bits-atmos.org/Events/img/enigma.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(33,
                " ",
                "Others",
                "THE MIDNIGHT AV QUIZ",
                1234669l,
                1234998l,
                "",
                "<p>Get the gears in motion for ATMOS, come join us for a Midnight AV quiz, a quiz solely for pleasure of quizzing. Have a battle of wits in rounds of Audio Visual Questions ensured to keep everyone thoroughly entertained and informed. Join us just after the Inauguration ceremony.</p><h2>Event Rules:</h2> <ul> <li>It will be a standard general quiz which will be held in two parts prelims and finals. Questions will majorly be Audio/Visual.</li> <li>Teams of three or less are allowed.</li> <li>The top 8 teams will advance to the finals.</li> <li>The winner gets prizes. The runner-ups get the joy of quizzing.</li> <li>Use of Internet is strictly prohibited. If caught, you will be disqualified immediately.</li> </ul> <h2>Eligibility Criteria:</h2> <p>This quiz is open to all and the only eligibility is that you must be present there.</p> <h2>Judging Criteria:</h2> <p>Standard quiz rules will be followed and the team with maximum points will be ranked first and so on.</br>In case of any controversy, the decision of the quizmaster will be final.</p> <h2>Registration:</h2> <p>There is no registration. Come, see and conquer. Open to all.</p> <h2>Contact:</h2> <p>Komal 7729987363</p>",
                "",
                "http://bits-atmos.org/Events/img/midnightAVQiuz.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(35,
                " ",
                "Others",
                "THE SCITECH QUIZ",
                1234669l,
                1234998l,
                "",
                "  <p>Do you think you know your technology, if so then, Test out your tech quotient, utilise the ancient art of deduction and reasoning combined with general awareness and your knowledge of technology and science on a battle to find the best. Join us for an afternoon of facts, fun and frolic.</p><h2>Event Rules:</h2> <ul ><li>It will be a standard quiz which will be held in two parts prelims and finals. Questions will majorly be based on Science and Technology.</li><li>Teams of three or less are allowed.</li> <li>The top 8 teams will advance to the finals.</li> <li>The final top three teams will get prize money</li> <li>Use of Internet is strictly prohibited. If caught, you will be disqualified immediately.</li> </ul> <h2>Eligibility Criteria:</h2> <p>This quiz is open to all and the only eligibility is that you must be present there.</p> <h2>Judging Criteria:</h2> <p>Standard quiz rules will be followed and the team with maximum points will be ranked first and so on.</br>In case of any controversy, the decision of the quizmaster will be final.</p> <h2>Registration:</h2> <p>Registration can be done online or on-spot by paying a minimal fee of Rs0.00 only.</p> <h2>Contact:</h2> <p>Komal 7729987363</p>",
                "",
                "http://bits-atmos.org/Events/img/Scitech.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(77,
                " ",
                "Others",
                "APP CONNECT",
                1234669l,
                1234998l,
                "",
                "<p>Last year we had AppConnect as our pre-Atmos event and got a fine response for it. Getting as many as 60 participants last year from various colleges, we publicized our headliner event, National B-Plan Competition Ground Reality, our Techno-Management fest Atmos and CEL Bits Pilani Hyderabad Campus itself and that too with zero costs. AppConnect is a platform for students to get forward with their idea for an app without having any technical knowledge to create one. The idea with the most feasible and revolutionary idea wins. Decision taken on based of certain factors including popularity judged by likes on facebook.</p>",
                "",
                "http://bits-atmos.org/Events/img/AppConnect.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(78,
                " ",
                "Others",
                "AD-O-MANIA",
                1234669l,
                1234998l,
                "",
                "<p>Henceforth we are aiming to increase our worth as a major entrepreneurial group by introducing more online events such as Ad-o-Mania. It is aimed to be a competition to create advertisements. The event currently includes a small quiz, logo and brand designing and advertisement poster creation. Being a new event, it still needs more updates.</p>",
                "",
                "http://bits-atmos.org/Events/img/adOMania.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(69,
                " ",
                "Others",
                "SUIT UP",
                1234669l,
                1234998l,
                "",
                "<p>The annual techno-management fest of BITS Pilani, Hyderabad Campus, ATMOS features a varied selection of events that are geared towards bringing to the forefront the knowledge of cutting edge technology and brave hearted leadership in the young generation. SuitUp is one of the most hotly awaited challenges staged in ATMOS where teams of up to 4 get their brain cells working. It involves using common sense and a sharp business acumen to get in front of the competition and to head their company to profit. On their way they have to take multiple decisions, each of which influences their profitability. The teams have to start with the funding which they invest in multiple areas such as hiring, land and licenses, R&D and marketing. The event has been a success ever since its inception and saw upwards of 60 participants last year. It is a challenge designed to make people think on their feet, to make them responsible for their way of thought and reward them just the same. The success of this event lies in the core essence that markets- in all sectors- are in fact dynamic.  The change in the market in SuitUp is represented by the algorithm used to calculate the company’s holdings at the end of every step that gets them closer to the end. The algorithm used to define the profit one receives has to have multiple check points to ensure the competition reflects the real world the best it can. The fact that human interaction is necessary in the form of bribing the city officials to get their desired licenses is an interesting feature of this event which makes it that more interesting. </p>",
                "",
                "http://bits-atmos.org/Events/img/SuitUP.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(70,
                " ",
                "Others",
                "Innovatia",
                1234669l,
                1234998l,
                "",
                "<p>This event is meant to test students on their spontaneity, creativeness and competitiveness.Each team will be given a prop randomly and they have to list alternative best 4-5 uses of the prop. Each team will then be paired with another team. Then each team gives a problem statement to the other team based on the uses listed by them. This round would be judged on how much concise, precise and challenging the problem statement is. In the next round each team would have to give a solution to the given problem statement. The teams at the end may showcase the uses of the props practically if they so wish to.</p>",
                "",
                "http://bits-atmos.org/Events/img/innovatia.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(23,
                " ",
                "Others",
                "Hackmania",
                1234669l,
                1234998l,
                "",
                "<p>A 2 day hackathon on Smart Campus/Smart City Initiative. To be the headliner event for CEL this time at ATMOS. Event to begin with a couple of speeches and briefing participants about Smart Campus/Smart City. At the time of the event, Mentors to be present to help and guide team brainstorm, ideate and develop their prototypes. At the end of second day, judging to take place. Looking forward to collaborate with certain organizations/start-ups for the same.</p>",
                "",
                "http://bits-atmos.org/Events/img/HackmaniaCEL.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(79,
                " ",
                "Others",
                "Talks",
                1234669l,
                1234998l,
                "",
                "<p>A couple of talks to be organized either in collaboration with Embryo of by ourselves related to start-ups and entrepreurship.</p>",
                "",
                "http://bits-atmos.org/Events/img/TAlksCEL.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(67,
                " ",
                "Others",
                "Bob The Green Builder",
                1234669l,
                1234998l,
                "",
                "<p>In this world of increased pollution and constantly degrading resources, join BITS Pilani Hyderabad Campus in developing a stable and an economical Green building Structure. The participants would be given a problem statement along with the description (Cost, etc.) of Green Building materials. The most stable, feasible and economical Green building structure solution will be awarded. Submit your entries to cea.bitshyd@gmail.com by October 7th, 2015 Don't find fault; find a remedy.GO GREEN</br></br></b>Rules:</b></br<br></p><ul><li>It is an online event.</li><li> Problem statement would be announced on the website.</li><li>The most stable, eco-friendly and economically feasible solution would be awarded. </li><li>The entries have to be submitted online to cea.bitshyd@gmail.com, latest by 10th of October. </li><li>The judges include eminent professors from Department of Civil Engineering, BITS Pilani Hyderabad Campus. </li><li>The results will be announced during the festival. </li><li>The decision of the judges and event head shall be treated as final and binding on all.</li></ul> <p></p><h2>Contact</h2> <p>Satya: +91-7730835423</br><br>Hryidesh Tewani: +91-9000755319</p>",
                "",
                "http://bits-atmos.org/Events/img/Bob_the_great_Builder.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(72,
                " ",
                "Others",
                "Open Outcry",
                1234669l,
                1234998l,
                "",
                "<p>A simulated gaming environment based on the pit-trading / open outcry system where trading of stocks is carried out using verbal pitches by traders and acknowledgement of the orders by the investors . Stocks are issued to the participants (participation in teams) in the form of IPOs by the organizers. Bid appropriately according to the market news and maintain your position as an investor in the market in order to regulate the price of  the stock. Abilities of the team members such as market analysis and split second judgements will come in handy. In order to win, drive up your net worth by using shrewd or cunning investment strategies and stand out of the crowd.</p><h4>Contact:</h4> <p >Tirthankar: +91-7729076444</p>",
                "",
                "http://bits-atmos.org/Events/img/outcry.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(73,
                " ",
                "Others",
                "Capital Warfare",
                1234669l,
                1234998l,
                "",
                "<p>A strategy game based on survival skills: Compete with other teams by taking rational decisions for your industry and stabilizing your country's economy. Teams will be allotted an industry or sector of a particular country (given by the organizers at the time of the event). Several rounds in the game will focus on different aspects of the teams such as growth, market influence, liquidity position, global outreach etc. The game will test your abilities of critical thinking and risk management strategies amongst other macro and micro economic aspects. Survive the various rounds of the game and be the last one standing to emerge as the winner.</p><h4>Contact:</h4> <p>Tirthankar: +91-7729076444</p>",
                "",
                "http://bits-atmos.org/Events/img/capitalWarfare.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(80,
                " ",
                "Others",
                "Conclave Talks",
                1234669l,
                1234998l,
                "",
                "<p>A lecture series which brings public speakers of the highest caliber from the worlds of academia, technology and management to our campus to share their thoughts and ideas.</p>",
                "",
                "http://bits-atmos.org/Events/img/conclavetalks.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(77,
                " ",
                "Others",
                "QuickHire",
                1234669l,
                1234998l,
                "",
                "<p>Stockroom Presents QuickHire - A Speed dating event to find your next dream internship at amazing startups in association with ATMOS, BITS-Pilani Hyderabad Campus. </p> <p>Date : 10th October </br>Venue : BITS Pilani Hyderabad Campus</p> <p>Register yourself by filling out this form :</p>",
                "",
                "http://bits-atmos.org/Events/img/stockroom.jpg",
                0,
                0d,
                0);


        tableManager.addEntry(47,
                " ",
                "Workshops",
                "Petroleum Refining Process",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<p style = \"font-size:20px;\"><b>Date of Workshop:</b>11th Oct 2015</p>\n" +
                        "                                <p style=\"font-size:20px;\">The main objective of the workshop is to share experiences in the field of petroleum refining among the potential engineers and nurturing young engineers to take up careers in petroleum sector. A practical session (laboratory demonstration) on product analysis is also organized to get familiarization about the various product-testing methods. The workshops will be helpful for updating the technical know-how of the participants.</br></br>The whole workshop will be consisting of four sessions. The session and activity/topic details are as follows:</p>\n" +
                        "                                \n" +
                        "                                <h2>Session-1: 9 am-11 am:</h2>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Introduction and statistical information about crude & refinery</li>\n" +
                        "                                    <li>Idea about Crude/ product characterization and analysis methods</li>\n" +
                        "                                    <li>Major products from refinery</li>\n" +
                        "                                    <li>Q/A</li>\n" +
                        " \n" +
                        "                                </ul>\n" +
                        "                                <h2>Session-2: 11.30 am-1.30pm </h2>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Practical demonstration</li>\n" +
                        "                                    <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Determination of cloud and pour point of diesel</li>\n" +
                        "                                    <li>Determination of flash point of kerosene/diesel</li>\n" +
                        "                                    <li>Determination of RVP of gasoline/naphtha</li>\n" +
                        "                                    <li>Determination of KV of engine oil using Redwood Viscomer</li>\n" +
                        "                            \n" +
                        "                                    <li>Determination of aniline point of diesel/kerosene</li>\n" +
                        "                                    <li>D86 Distillation characteristics of crude oil/ crude products</li>\n" +
                        "                                    \n" +
                        "                                     </ul>\n" +
                        " \n" +
                        "                                </ul>\n" +
                        "                                <h2>Session-3: 2.30 am-4.30pm </h2>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Idea about various conversion process like thermal & catalytic cracking</li>\n" +
                        "                                    <li>Major treatment process</li>\n" +
                        "                                    <li>Environmental aspects</li>\n" +
                        "                                    <li>Q/A</li>\n" +
                        " \n" +
                        "                                </ul>\n" +
                        "                                 <h2>Session-4: 4.45 am-5.30pm </h2>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Quiz on workshop</li>\n" +
                        "                                    <li>Distribution of Certificates for all and Prizes (best 3 based on quiz)</li>\n" +
                        "                            \n" +
                        " \n" +
                        "                                </ul>\n" +
                        "\n" +
                        "                                 <p style=\"font-size:20px;\">Registration fee per participant: Rs. 600/- by cash </br></p>\n" +
                        "\n" +
                        "                                 <h2>Contact details:</h2>\n" +
                        "                                 <p style = \"font-size:20px;\">Santhosh Raju V V-9966953366</p>",
                "",
                "http://bits-atmos.org/Events/img/petroliumWorkshop.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(81,
                " ",
                "Workshops",
                "Aspen Plus Process Modelling",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<p style = \"font-size:20px;\"><b>Date of Workshop:</b> 9th Oct 2015</p>\n" +
                        "                                <p style=\"font-size:20px;\">ASPEN is one of the most useful and widely used software for modeling in chemical engineering today.†To improve the design, operation, safety, and productivity of a chemical processes,†ASPEN†Tech process flow simulator is used to solve the problems faster and more reliably.†Acknowledging the importance of using such software, the chemical engineering department BITS, Pilani-Hyderabad Campus has decided to conduct a workshop on ASPEN this ATMOS -2015.††Those enrolled for this workshop will get to learn the basics of modeling and the unique features and benefits of using the ASPEN software.†In this†workshop†we will teach you on how to work on Process Modeling module to conduct steady state simulations. You will be learning the following:</p>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li> Basic of Process Modeling GUI</li>\n" +
                        "                                    <li>Process flow sheeting</li>\n" +
                        "                                    <li>Physical Properties</li>\n" +
                        "                                    <li> Regression of VLE data from NIST</li>\n" +
                        "                                    <li>Basic Reactors</li>\n" +
                        "                                    <li>Separation Processes</li>\n" +
                        "                                    <li>Integrated Flow sheeting Simulation.</li>\n" +
                        "\n" +
                        "                                </ul>\n" +
                        "\n" +
                        "                              \n" +
                        "                                <p style = \"font-size:20px;\">Registration fee per participant: Rs. 650/- by cash</p>\n" +
                        "                                <h2>Contact details:</h2>\n" +
                        "                                 <p style = \"font-size:20px;\">Tauras Marwaha-8499001706</p>\n" +
                        "\n",
                "",
                "http://bits-atmos.org/Events/img/aspenWorkshop.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(42,
                " ",
                "Workshops",
                "Prototype 3D",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<p style = \"font-size:20px;\"><b>Date Of Workshop:</b>10th Oct 2015</p>\n" +
                        "                                <p style=\"font-size:20px;\">Youíve probably heard of a 3D printer, a machine reminiscent of the Star Trek Replicator, something magical that can create objects out of thin air. It can ìprintî in plastic,†metal, even Chocolate and over a hundred other materials. It can be used for making nonsensical little models like the Iron man mask, yet it can also print manufacturing prototypes & end user products,†such as Semi-lethal guns,†aircraft engine parts†and even human organs using a personís own cells.</br>So hereís your chance to witness this Revolution which has swept the world of traditional manufacturing off their feet and has introduced the concept of personalizing your own products.</p>\n" +
                        "                                \n" +
                        "                      \n" +
                        "                                <p style=\"font-size:20px;\">The workshop will cover</p>\n" +
                        "                                <ul style=\"font-size:20px;\">\n" +
                        "                                    <li>The Basics of 3D Printing: A brief overview of the history, Techniques and the applications of 3D printing in the modern world.</li>\n" +
                        "                                    <li>Basics of CAD and CAM designing </li>\n" +
                        "                                    <li>A LIVE Demonstration of the print</li>\n" +
                        "                                </ul>\n" +
                        "                                <p style=\"font-size:20px;\"></p>\n" +
                        "                                \n" +
                        "                                 <h2>Contact details:</h2>\n" +
                        "                                 <p style = \"font-size:20px;\">Shaunak Aggarwal- 7729026444</br>\n<br>" +
                        "Bhaskar Soman-9490074810</p>",
                "",
                "http://bits-atmos.org/Events/img/prototype_3d.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(7,
                " ",
                "Workshops",
                "Hacked Off",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<p style=\"font-size:20px;\">The phrase 'Ethical Hacking' has become the talk of the town in the modern technological scenario. ATMOS'15 bring you Hacked Off- Ethical Hacking & Cyber Forensics workshop, a national level workshop based on Cyber Security in association with India's leading information security & communication technology training company, Authentic Techs.\n" +
                        "                                                    <br>\n" +
                        "                                                    This workshop on Ethical Hacking is an exhaustive hands on training program on real life computer hacking designed to meet the industry needs and provide students with placements. This course would provide the participants knowledge and experience on the fast growing field of Cyber Security.\n" +
                        "                                                    Hacked Off aims to educate users of digital media of the threats, risks and privacy concerns that go with using them. The other goal of this training module is to expose issues and vulnerabilities to drive the digital media industry to create solutions to these problems.\n" +
                        "                                                    <br>\n" +
                        "                                                    The whole workshop will be consisting of four sessions. The session and activity/topic details are as follows:\n" +
                        "                                                    <br><br>\n" +
                        "\n" +
                        "                                                    <b>Day 1 (Session 1) </b><br>\n" +
                        "                                                    Introduction on Ethical Hacking- Provides a slight view about the syllabus to be covered in the workshop. \n" +
                        "                                                    <br>\n" +
                        "                                                    Website Attacks:  Types of attacks will be explained on the websites and how they are hacked (Live demonstrations will be provided). \n" +
                        "                                                     <br>\n" +
                        "                                                    <b>Day 1 (Session 2) </b><br>\n" +
                        "                                                    Web Server Attacks: Knowledge about Web Servers and attacks on them, Tracing a Website, Complete info about a website and tracing the attacker's gateway (Live demonstrations will be provided). \n" +
                        "                                                    <br>\n" +
                        "                                                    Facebook Attacks: Various attacks on Facebook profiles will be explained and how can you hack into a facebook account as well as the safety tips. (Live demonstrations will be provided). \n" +
                        "                                                     <br>\n" +
                        "                                                    E-Mail Attacks: Various attacks on e-mail accounts will be explained and how can you hack into an email account as well as the safety tips. (Live demonstrations will be provided). \n" +
                        "                                                      <br><br>\n" +
                        "                                                    <b>Day 2 (Session 3)</b><br>\n" +
                        "                                                    Cloud Computing: A detailed intro about cloud computing. (Live demonstrations will be provided).  \n" +
                        "                                                     <br>\n" +
                        "                                                    System Hacking: Various possible attacks on a computer system will be shown about how the system can be hacked. Safety tips will be provided. (Live demonstrations will be provided). \n" +
                        "                                                    <br>\n" +
                        "                                                    <b>Day 2 (Session 4)</b><br>\n" +
                        "                                                     \n" +
                        "                                                    Virus & Trojans: Various types of viruses and trojans will be discussed and how to code a virus will be displayed ((Live demonstrations will be provided). \n" +
                        "                                                     <br>\n" +
                        "                                                    Software Cracking/Reverse engineering: How you can turn a trial version software into a full version will be displayed. (Live demonstrations will be provided). \n" +
                        "                                                     <br>\n" +
                        "                                                    Mobile Devices: An introduction on Mobile Hacking as well as applications will be provided. (Live demonstrations will be provided). \n" +
                        "                                                    <br><br>\n" +
                        "                                                    <b>Prerequisites:</b><br>\n" +
                        "                                                    None.\n" +
                        "                                                    <br><br>\n" +
                        "                                                    <b>Benefits</b><br>\n" +
                        "                                                    Internationally Valid Certifications from ATMOS'15 in association with Authentic Techs<br>\n" +
                        "                                                    Live Hacks<br>\n" +
                        "                                                    Cyber Security E-Toolkit <br>\n" +
                        "                                                    1 year free subscription to Authentic Techs services<br><br>\n" +
                        "                                                     \n" +
                        "\n" +
                        "                                                     \n" +
                        "                                                    <b>Registration:</b><br>\n" +
                        "                                                    Registration fee per participant:  RS 1200/- by cash. <br>\n" +
                        "                                                    <b>Note:</b> - All Participants are requested to bring their own Laptops.<br>\n" +
                        "                                                    <br><br><b>Contact:</b><br>\n" +
                        "                                                    Yogesh - 7730867444<br>Deepak - 9700449825<br><br>\n" +
                        "                                                     <p><b>Date and Time:</b>10th-11th October:&nbsp;10AM-6PM</p>\n" +
                        "\n" +
                        "\n" +
                        "                                                                                    </p>",
                "",
                "http://bits-atmos.org/Events/img/HackedOFFCSE.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(5,
                " ",
                "Workshops",
                "Fastrack Automobiles",
                1234669l,
                1234998l,
                "FootBall Ground",
                " <h2>Fastrack assembly disassembly workshop by Ezenith</h2>\n" +
                        "                                            <h3>Date : 9th and 10th of October</h3>\n" +
                        "                                        <p style = \"font-size:20px;\">The idea is to give participants a hands on experience on live, working engines. Participants get to work on 4 different engines, 2-stroke, 4 stroke, petrol car, and diesel car engines. The participants get a valuable experience of opening the engine inside out, observing the parts and functions that keep the engine functioning. To ensure everyone grasps the working, an R&D expert will be present at all times to guide students and quench their curiosity. At the end of the two day workshop, a certificate and an internship certificate are provided to the students. This workshop is a perfect blend of knowledge and thrill where each team races the remaining. </p>\n" +
                        "                                        <p style = \"font-size:20px;\">Registration fee : 1500/- </br> No Prerequisites required !!</p>\n" +
                        "                                        <p style = \"font-size:20px;\">Contact details</br><br>\n" +
                        "</br>" +
                        "Kushankur Dutta: +919603812054</br><br>\n</br>" +
                        "Mithilesh Mundhada: +918466095758</p>",
                "",
                "http://bits-atmos.org/Events/img/FastrackAtuomobiles.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(82,
                " ",
                "Workshops",
                "Bridge design, Fabrication and Testing",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<h2>Bridge design, Fabrication and Testing</h2>\n" +
                        "                                        <p style = \"font-size:20px;\">This workshop gives participants a unique opportunity to experience a Hands-on feel of a Civil Engineering mini project.</p>\n" +
                        "\n" +
                        "                                        <h2>Workshop Content</h2>\n" +
                        "\n" +
                        "                                        <ul style = \"font-size:20px;\">\n" +
                        "                                            <li>Introduction to Different Types of bridges existing.</li>\n" +
                        "                                            <li>The basic concept involved in designing of different bridges.</li>\n" +
                        "                                            <li>Real life case studies for actual understanding of Bridge Design concepts.</li>\n" +
                        "                                            <li> Designing and analyzing the bridge using CAD model.</li>\n" +
                        "                                            <li>Load calculations and stress analysis in various trusses using CAD model.</li>\n" +
                        "                                            <li>Bridge design issues arising during practical sessions.</li>\n" +
                        "                                            <li>Fabrication of their respecting bridge using balsa wood and testing for the maximum load taken to measure efficiency.</li>\n" +
                        "                                            <li>Group discussions and presentations for ideas and innovations.</li>\n" +
                        "\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                        <h2>Workshop Details</h2>\n" +
                        "                                        <ul style = \"font-size:20px;\">\n" +
                        "                                            <li>1 day workshop 10th Oct 2015 (8-9 hrs.)-includes lecture, design, fabrication and testing session.</li>\n" +
                        "                                            <li>Cost: Rs. 1200/- per participant.</li>\n" +
                        "                                            <li>Cost includes fabrication and training material.</li>\n" +
                        "                                        </ul>\n" +
                        "                                        <h4>Contact:</h4>\n" +
                        "                                        <p style= \"font-size:20px;\">Krishna Mvs: +91-9542969298</br><br>\n" +
                        "</br>" +
                        "Prasad Jain: +91-7731060188</p>\n" +
                        "                                        <h4>For Further Details:</h4>\n" +
                        "                                        <p style= \"font-size:20px;\"><a href = \"http://www.robostrat.com/\">http://www.robostrat.com/</a></p>",
                "",
                "http://bits-atmos.org/Events/img/bridge.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(24,
                " ",
                "Workshops",
                "Workshop on ETABS",
                1234669l,
                1234998l,
                "FootBall Ground",
                " <h2>About The Workshop</h2>\n" +
                        "                                                                                   <p style=\"font-size:20px;\"> The innovative and revolutionary new ETABS is the ultimate integrated software package for the structural analysis and design of buildings. ETABS offers unmatched 3D object based modeling and visualization tools, blazingly fast linear and nonlinear analytical power, sophisticated and comprehensive design capabilities for a wide-range of materials, and insightful graphic displays, reports, and schematic drawings that allow users to quickly and easily decipher and understand analysis and design results.  \n" +
                        "                                                    Intuitive drawing commands allow for rapid generation of floor and elevation framing. CAD drawings can be converted directly into ETABS models or used as templates onto which ETABS objects may be overlaid. The state-of-the-art SAPFire 64-bit solver allows extremely large and complex models to be rapidly analyzed, and supports nonlinear modeling techniques such as construction sequencing and time effects (e.g., creep and shrinkage).<br>   \n" +
                        "                                                    Design of steel and concrete frames (with automated optimization), composite beams, composite columns, steel joists, and concrete and masonry shear walls is included, as is the capacity check for steel connections and base plates. Models may be realistically rendered, and all results can be shown directly on the structure. Comprehensive and customizable reports are available for all analysis and design output, and schematic construction drawings of framing plans, schedules, details, and cross-sections may be generated for concrete and steel structures.ETABS provides an unequaled suite of tools for structural engineers designing buildings, whether they are working on one-story industrial structures or the tallest commercial high-rises.  </p>\n" +
                        "                                                                                    <h2>Theoritical Study</h2>\n" +
                        "                                                                                    <p> <b>Mechanics:</b>Shear Force, Bending Moment, Stresses and relation between these terms<br><b>Loads:</b> Different type of Loads and their calculation according to BIS <br><b>Theory of Structures:</b> Structural Analysis of simple and composite member by different theories  <br><b>Design:</b> R.C.C structures like beam, column, slab etc. and implementation of these aspects in real life time projects  <br><b>Material:</b> Various construction Materials, their behavior and use in construction projects  <br><b>Execution:</b> Construction management and execution of various civil projects.  </p>\n" +
                        "                                                                                    <h2>Duration\n" +
                        "                                                    </h2>\n" +
                        "                                                                                    <p>The workshop will be conducted by Texel Group. A three days workshop comprising of 3 sessions of 3 hours each to be provided on ETABS to the participants with hands on session on theory as well as practical problems.  \n" +
                        "                                                    </p>             <h2>Benefits</h2>\n" +
                        "                                                    <p>1) A competition will be organised by the Texel Group Engineers among the participants after the workshop (in the last session).Top 5 students from the workshop will be selected to undergo any training by Texel Group absolutely free in that particulate academic year.<br>  2) The workshop will provide knowledge of soft computing application by giving an overview of all the tools of E-Tabs    and introducing the needs of the current applications. <br> 3) Certificates of Merit, Participation and Coordination will be awarded to the respective participants</p> \n" +
                        "                                                    <h2>Registration</h2>\n" +
                        "                                                    <p> Events + E-Tabs Workshop (for outstation participants) = Rs.800 <br>\n" +
                        "                                                    \n" +
                        "                                                    Note: - All Participants are requested to bring their own Laptops.</p>\n" +
                        "                                                    <p>For registration <a href=\"http://www.texelgroup.com/workshop/registration#mid\">Click Here</a></p>\n" +
                        "                                                    <h2>Conatct Details:</h2>\n" +
                        "                                                    <p style = \"font-size:20px;\">Satya: +91-7730835423</p>",
                "",
                "http://bits-atmos.org/Events/img/civilETABSworkshop.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(83,
                " ",
                "Workshops",
                "Augmented reality",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<h2>Topics to be covered</h2>   \n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Basics of computer Systems</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li>Introduction to computer system</li>\n" +
                        "                                            <li>Types of system</li>\n" +
                        "                                            <li>Difference b/w GUI and NUI</li>\n" +
                        "                                            <li>Introduction to natural inputs </li>\n" +
                        "                                        </ul>\n" +
                        "                                    </li>\n" +
                        "                                    <li>Touch technologies</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li>Introduction to Resistive touch</li>\n" +
                        "                                            <li>Working of resistive touch</li>\n" +
                        "                                            <li>Applications of resistive touch</li>\n" +
                        "                                            <li>Introduction to Capacitive touch</li>\n" +
                        "                                            <li>Working of capacitive touch</li>\n" +
                        "                                            <li>Applications of capacitive touch</li>\n" +
                        "                                        </ul>\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "                                    <li>Introduction to Surface computing</br>\n" +
                        "                                        Introduction to different types of multi touch technologies\n" +
                        "                                        <ul>\n" +
                        "                                            <li>FTIR</li>\n" +
                        "                                            <li>DI</li>\n" +
                        "                                            <li>DSI</li>\n" +
                        "                                            <li>LED-LP</li>\n" +
                        "                                            <li>LLP</li>\n" +
                        "                                            \n" +
                        "                                        </ul>\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "                                    <li>Creating a touch pad</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li>Making a touch pad setup</li>\n" +
                        "                                            <li>Running few application</li>\n" +
                        "                                            <li>Drive mouse pointer using that touch pad</li>\n" +
                        "                                            <li>Introduction to create touch screen</li>\n" +
                        "                                            <li>Introdution to few gaming consoles</li>\n" +
                        "\n" +
                        "                                            <li>Give desired controls to your games</li>\n" +
                        "                                            <li>Coding to make desired control</li>\n" +
                        "                                            <li>Move mouse pointer using accelerometer</li>\n" +
                        "                                            <li>Making a interactive touch wall</li>\n" +
                        "                                            \n" +
                        "                                        </ul>\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "                                    <li>Gesture computing using Kinect</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li> Introduction to Kinect</li>\n" +
                        "                                            <li>Working of kinect</li>\n" +
                        "                                            <li>Application of kinect</li>\n" +
                        "                                            <li>Kinect with windows</li>\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                    </li>\n" +
                        "                                    <li>Introduction to image and pixels</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li>Introduction to images</li>\n" +
                        "                                            <li> Introduction to Pixels</li>\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "                                    <li>Working with Image and Pixels</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li>How images are formed?</li>\n" +
                        "                                            <li>How to read pixels</li>\n" +
                        "                                            <li>Interaction with system using pixel recognition</li>\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "                                    <li>Open Natural Interface</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li>What is Open NI?</li>\n" +
                        "                                            <li>Natural User Interface Software Setup</li>\n" +
                        "                                            <li>How Open NI Works?</li>\n" +
                        "                                            <li>Installation of SimpleOpen NI</li>\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "\n" +
                        "                                    <li>Programming language</br>\n" +
                        "                                        <ul>\n" +
                        "                                            <li> Introduction to Processing</li>\n" +
                        "                                            <li>What is processing?</li>\n" +
                        "                                            <li>What are libraries?Installation Of Libraries</li>\n" +
                        "                                            <li>Building your first Sketch (Program)</li>\n" +
                        "                                        </ul>\n" +
                        "\n" +
                        "                                    </li>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                    \n" +
                        "                                    \n" +
                        "                                </ul>                                           \n" +
                        "                                <h2>After the workshop, Students learn :</h2>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>How to create a touch pad and touch screen simple using a web camera?</li>\n" +
                        "                                    <li> How QR code, BAR code and fiducial can be read?</li>\n" +
                        "                                    <li>How to convert their Normal Laptop Screen into Touch Screen?</li>\n" +
                        "                                    <li>How to create virtual mouse for your system?</li>\n" +
                        "                                </ul>   \n" +
                        "\n" +
                        "                                <h2>Take away for participants:</h2>\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>Certification: Each Participant will get a participation certificate.</li>\n" +
                        "                                    <li>Toolkit:</br>Each participant will get a toolkit containing various software and applications.</br>Each participants will get EBook and future guidance for their projects.</li>\n" +
                        "                                    <li>Summer/Winter Training:20% Discount on summer & Winter Training Program by SenseFil Technologies.</li>\n" +
                        "                                </ul>   \n" +
                        "\n" +
                        "                                <h2> For further Details:</h2>\n" +
                        "                                <p>Contact: Chandra Kiran-7731045306</p>\n" +
                        "                                <p style = \"font-size:20px;\"><a href = \"http://www.sensefil.com/\">http://www.sensefil.com/</a></p>",
                "",
                "http://bits-atmos.org/Events/img/augmentedR.jpg",
                0,
                0d,
                0);
        tableManager.addEntry(22,
                " ",
                "Workshops",
                "IoT based System Design using NI Platform",
                1234669l,
                1234998l,
                "FootBall Ground",
                "<h3>Date: 9th and 10th October</h3>\n" +
                        "                                <p style = \"font-size:20px;\">The Internet of Things (IoT) is having a profound effect on the discipline of system design. Product design, manufacturing and infrastructure are all affected by this technology evolution. So how are the students of today being prepared for the complexity of our interconnected future? In this workshop, you will Hear more about the ways that NI is partnering with educators and academic researchers to bring the vision of IoT to reality.</br>Registration Fee:Rs.500</p>\n" +
                        "                                <h4>Agenda:</h4>\n" +
                        "\n" +
                        "                                <h5></br>Day 1</h5>\n" +
                        "\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>       - Introduction to IoT - NI way</li>\n" +
                        "                                    <li>        - Hands on training on Graphical System Design software LabVIEW</li>\n" +
                        "                                    <li>        - Break</li>\n" +
                        "                                    <li>        - Hands on training on Graphical System Design software LabVIEW</li>\n" +
                        "                                    <li>        - Lunch</li>\n" +
                        "                                    <li>        - Acquire - Analyze - Automate using myDAQ</li>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </ul>\n" +
                        "\n" +
                        "                                <h5></br>Day 2</h5>\n" +
                        "\n" +
                        "                                <ul style = \"font-size:20px;\">\n" +
                        "                                    <li>        - Introduction to NI IoT platform (myRIO)</li>\n" +
                        "                                    <li>        - Hands on training on myRIO</li>\n" +
                        "                                    <li>       - Lunch</li>\n" +
                        "                                    <li>        - Design - Prototype - Deploy using myRIO for IoT applications</li>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </ul>\n" +
                        "\n" +
                        "                                                          <h2>Contact Details:</h2>\n" +
                        "                                                         <p style = \"font-size:20px;\">Chandra kiran: 7731045306</p>",
                "",
                "http://bits-atmos.org/Events/img/internetOfThings.jpg",
                0,
                0d,
                0);


    }

}