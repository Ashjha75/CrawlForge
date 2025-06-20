<div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card" style="background-color: #2b2d30; color: white;">
                    <div class="card-header text-center">
                        <h3>Feedback Form</h3>
                    </div>
                    <div class="card-body p-4">
                        <form action="<%=application.getContextPath()%>/hibernateFeedback" method="post">
                            <div class="mb-3">
                                <label for="exampleInputEmail1" class="form-label">Email address</label>
                                <input type="email" name="email" class="form-control bg-dark text-white" id="exampleInputEmail1" aria-describedby="emailHelp">
                                <div id="emailHelp" class="form-text text-primary text-white ">We'll never share your email with anyone else.</div>
                            </div>
                            <div class="mb-3">
                                <label for="exampleInputPassword1" class="form-label">Mobile Number</label>
                                <input type="text" name="mobile" class="form-control bg-dark text-white" id="exampleInputPassword1">
                            </div>
                            <div class="mb-4">
                                <label for="feedbackText" class="form-label">Your Feedback</label>
                                <textarea class="form-control bg-dark text-white" name="feedback" id="feedbackText" rows="4" placeholder="Please enter your feedback here"></textarea>
                            </div>
                            <div class="text-center">
                                <button type="reset" class="btn btn-light px-4">Reset</button>
                                <button type="submit" class="btn btn-primary px-4">Submit</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>