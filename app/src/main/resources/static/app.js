const statusOutput = document.getElementById("status-output");
const usersOutput = document.getElementById("users-output");
const jobsOutput = document.getElementById("jobs-output");
const applicationsOutput = document.getElementById("applications-output");

function showStatus(message, isError = false) {
  statusOutput.textContent = `${new Date().toLocaleTimeString()} - ${message}`;
  statusOutput.style.color = isError ? "#fecaca" : "#d1e7ff";
}

async function apiRequest(path, options = {}) {
  const response = await fetch(path, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
    ...options,
  });

  const contentType = response.headers.get("content-type") || "";
  const hasJson = contentType.includes("application/json");
  const data = hasJson ? await response.json() : await response.text();

  if (!response.ok) {
    throw new Error(typeof data === "string" ? data : JSON.stringify(data, null, 2));
  }

  return data;
}

function readForm(formElement) {
  return Object.fromEntries(new FormData(formElement).entries());
}

document.getElementById("signup-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const values = readForm(event.target);

  try {
    const createdUser = await apiRequest("/users/create", {
      method: "POST",
      body: JSON.stringify({
        name: values.name,
        email: values.email,
      }),
    });

    const query = new URLSearchParams({
      userId: createdUser.id,
      password: values.password,
    });

    await apiRequest(`/auth/register?${query.toString()}`, { method: "POST" });

    const loginFormUserId = document.querySelector('#login-form input[name="userId"]');
    if (loginFormUserId) {
      loginFormUserId.value = String(createdUser.id);
    }

    showStatus(`Sign up successful. Linked auth to user id ${createdUser.id}.`);
  } catch (error) {
    showStatus(`Sign up failed: ${error.message}`, true);
  }
});

document.getElementById("login-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const values = readForm(event.target);
  const query = new URLSearchParams({
    userId: values.userId,
    password: values.password,
  });

  try {
    await apiRequest(`/auth/login?${query.toString()}`, { method: "POST" });
    showStatus(`Login call successful for user ${values.userId}.`);
  } catch (error) {
    showStatus(`Login failed: ${error.message}`, true);
  }
});

document.getElementById("create-user-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const values = readForm(event.target);

  try {
    const created = await apiRequest("/users/create", {
      method: "POST",
      body: JSON.stringify({
        name: values.name,
        email: values.email,
      }),
    });
    showStatus(`User created with id ${created.id}.`);
  } catch (error) {
    showStatus(`Create user failed: ${error.message}`, true);
  }
});

document.getElementById("load-users-btn").addEventListener("click", async () => {
  try {
    const users = await apiRequest("/users/all");
    usersOutput.textContent = JSON.stringify(users, null, 2);
    showStatus(`Loaded ${users.length || 0} users.`);
  } catch (error) {
    showStatus(`Load users failed: ${error.message}`, true);
  }
});

document.getElementById("create-job-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const values = readForm(event.target);

  try {
    const created = await apiRequest("/jobs/create", {
      method: "POST",
      body: JSON.stringify({
        title: values.title,
        description: values.description,
        user: { id: Number(values.userId) },
        company: values.company,
        location: values.location,
        salary: Number(values.salary),
      }),
    });
    showStatus(`Job created with id ${created.id}.`);
  } catch (error) {
    showStatus(`Create job failed: ${error.message}`, true);
  }
});

document.getElementById("load-jobs-btn").addEventListener("click", async () => {
  try {
    const jobs = await apiRequest("/jobs/getAll");
    jobsOutput.textContent = JSON.stringify(jobs, null, 2);
    showStatus(`Loaded ${jobs.length || 0} jobs.`);
  } catch (error) {
    showStatus(`Load jobs failed: ${error.message}`, true);
  }
});

document.getElementById("apply-job-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const values = readForm(event.target);

  try {
    const application = await apiRequest("/applications/apply", {
      method: "POST",
      body: JSON.stringify({
        userId: Number(values.userId),
        jobId: Number(values.jobId),
      }),
    });
    showStatus(`Application created with id ${application.applicationId}.`);
  } catch (error) {
    showStatus(`Apply failed: ${error.message}`, true);
  }
});

document.getElementById("user-applications-form").addEventListener("submit", async (event) => {
  event.preventDefault();
  const values = readForm(event.target);

  try {
    const apps = await apiRequest(`/applications/user/${values.userId}`);
    applicationsOutput.textContent = JSON.stringify(apps, null, 2);
    showStatus(`Loaded ${apps.length || 0} applications for user ${values.userId}.`);
  } catch (error) {
    showStatus(`Load applications failed: ${error.message}`, true);
  }
});
